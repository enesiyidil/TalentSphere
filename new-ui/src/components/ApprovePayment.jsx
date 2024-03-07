import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {
    ACCEPTED_OR_REJECTED_HOLIDAY_BY_ID, ACCEPTED_OR_REJECTED_PAYMENT_BY_ID,
    API_GATEWAY_URL,
    FIND_ALL_BY_NOT_APPROVE,
    HOLIDAY_URL,
    PAYMENT_URL
} from "../constant/Endpoints.js";
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import styles from "../Css/ApprovePayment.module.css";
import {Paper, Table, TableBody, TableContainer, TableHead} from "@mui/material";

function Row(props) {
    const {row} = props;
    const [open, setOpen] = React.useState(false);

    return (

        <React.Fragment>
            <TableRow sx={{'& > *': {borderBottom: 'unset'}}}>
                <TableCell>
                    <IconButton
                        aria-label="expand row"
                        size="small"
                        onClick={() => setOpen(!open)}
                    >
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell align="right">{row.nameOfCreator}</TableCell>
                <TableCell align="right">{row.expenditureType}</TableCell>
                <TableCell align="right">{row.type}</TableCell>
                <TableCell align="right">{row.dueDate}</TableCell>
                <TableCell align="right">{row.updatedDate}</TableCell>
                <TableCell align="right">{row.requestDate}</TableCell>
                <TableCell align="right">{row.currency} {row.amount}</TableCell>
                <TableCell align="right">
                    <div className={styles["button-wrapper"]}>
                        <button onClick={e => row.handleApprove(e.target.value)} type="button"
                                className={styles["button"]} value={row.id}>
                            Approve
                        </button>
                        <button type='button' onClick={e => row.handleReject(e.target.value)}
                                className={styles["button"]}
                                value={row.id}>Reject
                        </button>
                    </div>
                </TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Typography variant="h6" gutterBottom component="div">
                            Description
                        </Typography>
                        <Typography variant="subtitle2" gutterBottom component="div">
                            {row.description}
                        </Typography>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>
    );
}

export function ApprovePayment() {
    const {apiPost, apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const data = useSelector((state) => state.data);
    const userProfile = useSelector((state) => state.userProfile);
    const [payments, setPayments] = useState([{
        id: "",
        nameOfCreator: "",
        amount: 0,
        dueDate: "",
        updatedDate: "",
        description: "",
        type: "",
        currency: "",
        expenditureType: "",
        requestDate: ""
    }]);

    useEffect(() => {
        const request = async () => {
            const response = await apiGet(`${API_GATEWAY_URL}${PAYMENT_URL}${FIND_ALL_BY_NOT_APPROVE}?companyId=${userProfile.companyId}`, token);
            if (response.status === 200) {
                setPayments(response.data);
            } else {
                alert(response.data.message);
            }
        }
        request()

    }, []);

    const handleApprove = (id) => {
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${PAYMENT_URL}${ACCEPTED_OR_REJECTED_PAYMENT_BY_ID}`, {id, confirm: 'accept'}, token);
            if (response.status === 200 && response.data === true) {
                setPayments(prevState => prevState.filter(item => item.id !== id))
            } else {

            }
        }
        request()

    }

    const handleReject = (id) => {
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${PAYMENT_URL}${ACCEPTED_OR_REJECTED_PAYMENT_BY_ID}`, {id, confirm: 'reject'}, token);
            if (response.status === 200 && response.data === true) {
                setPayments(prevState => prevState.filter(item => item.id !== id))
            } else {

            }
        }
        request()

    }

    return (
        <>
            <div style={{
                width: '80%', height: '20%', paddingTop: '1.5rem', margin: 'auto',
            }}>
                <TableContainer component={Paper}>
                    <Table aria-label="collapsible table">
                        <TableHead>
                            <TableRow>
                                <TableCell/>
                                <TableCell>Name Of Creator</TableCell>
                                <TableCell align="right">Expenditure Type</TableCell>
                                <TableCell align="right">Type</TableCell>
                                <TableCell align="right">Due Date</TableCell>
                                <TableCell align="right">Updated Date</TableCell>
                                <TableCell align="right">Request Date</TableCell>
                                <TableCell align="right">Amount</TableCell>
                                <TableCell align="right">Approve/Reject</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {payments.map(payment => <Row key={payment.id}
                                                          row={{...payment, handleApprove, handleReject}}/>)}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
        </>
    )
}
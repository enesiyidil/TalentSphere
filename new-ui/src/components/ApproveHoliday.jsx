import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {
    ACCEPTED_OR_REJECTED_COMMENT_BY_ID, ACCEPTED_OR_REJECTED_HOLIDAY_BY_ID,
    API_GATEWAY_URL,
    COMMENT_URL, FIND_ALL_BY_COMPANY_ID_AND_STATUS_PENDING,
    FIND_ALL_BY_NOT_APPROVE, HOLIDAY_URL
} from "../constant/Endpoints.js";
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import styles from "../Css/ApproveHoliday.module.css";
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
                <TableCell align="right">{row.name}</TableCell>
                <TableCell align="right">{row.personalName}</TableCell>
                <TableCell align="right">{row.startDate}</TableCell>
                <TableCell align="right">{row.endDate}</TableCell>
                <TableCell align="right">
                    <div className={styles["button-wrapper"]}>
                        <button onClick={e => row.handleApprove(e.target.value)} type="button" className={styles["button"]} value={row.id}>
                            Approve
                        </button>
                        <button type='button' onClick={e => row.handleReject(e.target.value)} className={styles["button"]}
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

export function ApproveHoliday() {
    const {apiPost, apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const data = useSelector((state) => state.data);
    const userProfile = useSelector((state) => state.userProfile);
    const [holidays, setHolidays] = useState([{name: "", id: 0, description: "", startDate: "", endDate: "", personalName: ""}]);

    useEffect(() => {
        const request = async () => {
            const response =await apiGet(`${API_GATEWAY_URL}${HOLIDAY_URL}${FIND_ALL_BY_COMPANY_ID_AND_STATUS_PENDING}?companyId=${userProfile.companyId}`, token);
            if (response.status === 200) {
                setHolidays(response.data);
            } else {

            }
        }
        request()

    }, []);

    const handleApprove = (id) => {
        const request = async () => {
            const response =await apiGet(`${API_GATEWAY_URL}${HOLIDAY_URL}${ACCEPTED_OR_REJECTED_HOLIDAY_BY_ID}?id=${id}&confirm=accept`, token);
            if(response.status === 200 && response.data === true) {
                setHolidays(prevState => prevState.filter(item => item.id !== id))
            }else {

            }
        }
        request()

    }

    const handleReject = (id) => {
        const request = async  () => {
            const response =await apiGet(`${API_GATEWAY_URL}${HOLIDAY_URL}${ACCEPTED_OR_REJECTED_HOLIDAY_BY_ID}?id=${id}&confirm=reject`, token);
            if(response.status === 200 && response.data === true) {
                setHolidays(prevState => prevState.filter(item => item.id !== id))
            }else {

            }
        }
        request()

    }

    return (
        <>
            <div style={{ width:'50%', height:'20%',paddingTop:'1.5rem',margin:'auto',
                }}>
            <TableContainer component={Paper}>
                <Table aria-label="collapsible table">
                    <TableHead>
                        <TableRow>
                            <TableCell />
                            <TableCell>Name</TableCell>
                            <TableCell align="right">Personal Name</TableCell>
                            <TableCell align="right">Start Date</TableCell>
                            <TableCell align="right">End Date</TableCell>
                            <TableCell align="right">Approve/Reject</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {holidays.map(comment => <Row key={comment.id} row={{...comment, handleApprove,handleReject}} />)}
                    </TableBody>
                </Table>
            </TableContainer>
            </div>
        </>
    )
}
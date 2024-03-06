import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {API_GATEWAY_URL, GET_INFORMATION, VISITOR_URL} from "../constant/Endpoints.js";
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import {Paper, Table, TableBody, TableContainer, TableHead} from "@mui/material";
import styles from "../Css/VisitorHome.module.css";


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
                <TableCell align="right">{row.companyName}</TableCell>
                <TableCell align="right">{row.managerName}</TableCell>
                <TableCell align="right">{row.createdDate}</TableCell>
                <TableCell align="right">{row.paymentNumber}</TableCell>
                <TableCell align="right">{row.turnOver}</TableCell>
                <TableCell align="right">{row.commentNumber}</TableCell>
            </TableRow>
            <TableRow>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={6}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Typography variant="h6" gutterBottom component="div">
                            Address
                        </Typography>
                        <Typography variant="subtitle2" gutterBottom component="div">
                            {row.address}
                        </Typography>
                    </Collapse>
                </TableCell>
            </TableRow>
        </React.Fragment>

    );
}

export function VisitorHome() {
    const {apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const search = useSelector((state) => state.search);
    const [information, setInformation] = useState([
        {
            managerName: "",
            address: "",
            createdDate: "",
            companyName: "",
            paymentNumber: 0,
            turnOver: 0,
            commentNumber: 0
        }
    ]);

    useEffect(() => {
        const request = async () => {
            const response =await apiGet(`${API_GATEWAY_URL}${VISITOR_URL}${GET_INFORMATION}`, token);
            if (response.status === 200) {
                setInformation(response.data);
            } else {

            }
        }
        request()

    }, []);

    return (

        <>
        <div className={styles["formWrapper"]}>
            <TableContainer  component={Paper}>
                <Table aria-label="collapsible table">
                    <TableHead>
                        <TableRow>
                            <TableCell/>
                            <TableCell>Company Name</TableCell>
                            <TableCell align="right">Manager Name</TableCell>
                            <TableCell align="right">Created Date</TableCell>
                            <TableCell align="right">Payment Number</TableCell>
                            <TableCell align="right">Turn Over</TableCell>
                            <TableCell align="right">Comment Number</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {information.map((info, index) => ((info.companyName.includes(search) || !search ) &&
                            <Row key={index} row={info}/>))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
        </>

    )
}
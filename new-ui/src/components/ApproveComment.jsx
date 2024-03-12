import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {
    ACCEPTED_OR_REJECTED_COMMENT_BY_ID,
    API_GATEWAY_URL,
    COMMENT_URL,
    FIND_ALL_BY_NOT_APPROVE
} from "../constant/Endpoints.js";
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import TableCell from '@mui/material/TableCell';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import styles from "../Css/ApproveComment.module.css";
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
                <TableCell align="right">{row.companyName}</TableCell>
                <TableCell align="right">{row.title}</TableCell>
                <TableCell align="right">{row.personalName}</TableCell>
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
                            Content
                        </Typography>
                        <Typography variant="subtitle2" gutterBottom component="div">
                            {row.content}
                        </Typography>
                    </Collapse>
                </TableCell>

            </TableRow>

        </React.Fragment>

    );
}

export function ApproveComment() {
    const {apiPost, apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const [comments, setComments] = useState([{personalName: "", id: 0, content: "", companyName: ""}]);

    useEffect(() => {
        const request = async () => {
            const response = await apiGet(`${API_GATEWAY_URL}${COMMENT_URL}${FIND_ALL_BY_NOT_APPROVE}`, token);
            if (response.status === 200) {
                setComments(response.data);
            } else {

            }
        }
        request()
    }, []);

    const handleApprove = (id) => {
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${COMMENT_URL}${ACCEPTED_OR_REJECTED_COMMENT_BY_ID}`, {
                id: id,
                confirm: 'accept'
            }, token);
            if (response.status === 200 && response.data === true) {
                setComments(prevState => prevState.filter(item => item.id != id));
            } else {

            }
        }
        request()
    }

    const handleReject = (id) => {
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${COMMENT_URL}${ACCEPTED_OR_REJECTED_COMMENT_BY_ID}`, {
                id: id,
                confirm: 'reject'
            }, token);
            if (response.status === 200 && response.data === true) {
                setComments(prevState => prevState.filter(item => item.id !== id));
            } else {

            }
        }
        request()
    }

    return (
        <>
            <div style={{width: '80%', margin: 'auto'}}>
                <TableContainer component={Paper}>
                    <Table aria-label="collapsible table">
                        <TableHead>
                            <TableRow>
                                <TableCell/>
                                <TableCell>Company Name</TableCell>
                                <TableCell>Title</TableCell>
                                <TableCell align="right">Personal Name</TableCell>
                                <TableCell align="right">Approve/Reject</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {comments.map(comment => <Row key={comment.id}
                                                          row={{...comment, handleApprove, handleReject}}/>)}
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>

        </>
    )
}
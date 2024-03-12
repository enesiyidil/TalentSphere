import styles from "../Css/PersonalHome.module.css";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {API_GATEWAY_URL, GET_INFORMATION, PERSONAL_URL} from "../constant/Endpoints.js";
import Container from '@mui/material/Container';
import {LinearProgress, Slide} from '@mui/material';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export function PersonalHome() {

    const {apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const [index, setIndex] = useState(0);
    const [progress, setProgress] = useState(0);
    const slideInterval = 5000;
    const [information, setInformation] = useState({
        managerName: "",
        company: {
            name: "",
            address: "",
            gallery: [],
            personalNumber: 0,
            communications: [{
                name: "",
                phoneNumber: ""
            }],
        },
        shift: {
            name: "",
            startTime: "",
            endTime: "",
            breaks: [{
                name: "",
                startTime: "",
                endTime: "",
            }]
        },
        comments: [{
            personalName: "",
            content: "",
            createdDate: ""
        }]
    });

    useEffect(() => {
        const request = async () => {
            const response = await apiGet(`${API_GATEWAY_URL}${PERSONAL_URL}${GET_INFORMATION}?id=${userProfile.id}`, token);
            if (response.status === 200) {
                setInformation(response.data);
            } else {

            }
        }
        request()

    }, []);

    useEffect(() => {
        const timer = setInterval(() => {
            setProgress((prevProgress) => {
                if (prevProgress === 100) {
                    setIndex((prevIndex) => {
                        if (information.company.gallery.length - 1 === prevIndex){
                            return 0;
                        }
                        return prevIndex + 1;
                    });
                    return 0;
                }
                return prevProgress + (100 / slideInterval) * 100;
            });
        }, 100);

        return () => clearInterval(timer);
    }, []);


    return (
        <>
            <div className={styles["personal-wrapper"]}>
                <div className={styles["container-wrapper"]}>
                    <Container maxWidth="sm">
                        {information.company.gallery ? (information.company.gallery.length > 0 ?
                                <Slide direction="right" in={true} object-fit=" cover ">
                                    <img src={information.company.gallery[index]} alt={`Slide ${index}`}
                                         style={{width: "25vw", height: "40vh"}}/>
                                </Slide> : information.company.gallery.length === 1 ?
                                    <img src={information.company.gallery[0]} alt={`${0}`}
                                         style={{width: "25vw", height: "40vh"}}/> :
                                    <img src="company.jpg" alt={`Company img`} style={{width: "25vw", height: "40vh"}}/>) :
                            <img src="company.jpg" alt={`Company img`} style={{width: "25vw", height: "40vh"}}/>
                        }
                        <LinearProgress variant="determinate" value={progress} className={styles["linear-progress"]}
                                        style={{width: "25vw"}}/>
                    </Container>
                    <Container maxWidth="sm">
                        <div className={styles["label"]}>
                            <div className={styles["textarea"]}>
                                <span>Name: </span>
                                <span>{information.company.name}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Address: </span>
                                <span>{information.company.address}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Personal Number: </span>
                                <span>{information.company.personalNumber}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Shift Name: </span>
                                <span>{information.shift.name}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Shift Start Time: </span>
                                <span>{information.shift.startTime}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Shift End Time: </span>
                                <span>{information.shift.endTime}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Communications Table: </span>
                                <TableContainer component={Paper} width="10%" height="100%" paddingTop="1.5rem">
                                    <Table aria-label="simple table">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Name</TableCell>
                                                <TableCell align="right">Phone Number</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {information.company.communications && information.company.communications.map((row) => (
                                                <TableRow
                                                    key={row.id}
                                                    sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                                >
                                                    <TableCell component="th" scope="row">
                                                        {row.name}
                                                    </TableCell>
                                                    <TableCell align="right">{row.phoneNumber}</TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                            </div>
                        </div>
                    </Container>
                </div>
                <div className={styles["container-wrapper"]}>
                    <Container maxWidth="xl"
                               style={{width: '90%', height: '20%', paddingTop: '1.5rem', margin: 'auto'}}>
                        <TableContainer component={Paper}>
                            <Table sx={{minWidth: 650}} aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Personal Name</TableCell>
                                        <TableCell align="right">Content</TableCell>
                                        <TableCell align="right">Created Date</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {information.comments && information.comments.map((row) => (
                                        <TableRow
                                            key={row.id}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell component="th" scope="row">
                                                {row.personalName}
                                            </TableCell>
                                            <TableCell align="right">{row.content}</TableCell>
                                            <TableCell align="right">{row.createdDate}</TableCell>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </Container>
                </div>
            </div>
        </>
    )
}
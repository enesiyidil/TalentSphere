import styles from "../Css/ManagerHome.module.css";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useDispatch, useSelector} from "react-redux";
import {API_GATEWAY_URL, GET_INFORMATION, MANAGER_URL} from "../constant/Endpoints.js";
import {setData} from "../redux/actions.js";
import Container from '@mui/material/Container';
import {LinearProgress, Slide} from '@mui/material';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export function ManagerHome() {

    const {apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const data = useSelector((state) => state.data);
    const userProfile = useSelector((state) => state.userProfile);
    const dispatch = useDispatch();
    const [index, setIndex] = useState(0);
    const [progress, setProgress] = useState(0);
    const slideInterval = 5000;

    useEffect(() => {
        const request = async () =>{
            const response =await apiGet(`${API_GATEWAY_URL}${MANAGER_URL}${GET_INFORMATION}?id=${userProfile.id}`, token);
            if (response.status === 200) {
                dispatch(setData(response.data));
            } else {

            }
        }
        request()

    }, []);

    useEffect(() => {
        const timer = setInterval(() => {
            setProgress((prevProgress) => {
                if (prevProgress === 100) {
                    setIndex((prevIndex) => (prevIndex + 1) % (data.gallery ? data.gallery.length : 1));
                    return 0;
                }
                return prevProgress + (100 / slideInterval) * 100;
            });
        }, 100);

        return () => clearInterval(timer);
    }, []);



    return (
        <>
            <div className={styles["manager-wrapper"]}>
                <div className={styles["container-wrapper"]}>
                    <Container maxWidth="sm" >
                        {data.gallery &&
                            <Slide direction="right" in={true}  object-fit=" cover ">
                                <img src={data.gallery[index]} alt={`Slide ${index}`}/>
                            </Slide>
                        }
                        <LinearProgress variant="determinate" value={progress}/>
                    </Container>
                    <Container maxWidth="xs">
                        <div className={styles["label"]}>
                            <div className={styles["textarea"]}>
                                <span>Name: </span>
                                <span>{data.name}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Address: </span>
                                <span>{data.address}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Personal Number: </span>
                                <span>{data.personals && data.personals.length}</span>
                            </div>
                            <div className={styles["textarea"]}>
                                <span>Communications Table: </span>

                                <div style={{width: '100%', height:'200%',paddingTop:'1.8rem'}}>
                                    <TableContainer component={Paper} >

                                        <Table aria-label="simple table" >

                                            <TableHead>

                                                <TableRow >
                                                    <TableCell>Name</TableCell>
                                                    <TableCell align="right">Phone Number</TableCell>
                                                </TableRow>
                                            </TableHead>

                                            <TableBody>


                                                {data.communications && data.communications.map((row) => (

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
                        </div>
                    </Container>
                </div>
                <div className={styles["container-wrapper"]}>
                    <div style={{ width:'90%', height:'20%',paddingTop:'1.5rem',margin:'auto'}}>
                        <Container maxWidth="xl">
                            <TableContainer component={Paper}>
                                <Table sx={{minWidth: 650}} aria-label="simple table">
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Description</TableCell>
                                            <TableCell align="right">Amount</TableCell>
                                            <TableCell align="right">Due Date</TableCell>
                                            <TableCell align="right">Type</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {data.payments && data.payments.map((row) => (
                                            <TableRow
                                                key={row.id}
                                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                            >
                                                <TableCell component="th" scope="row">
                                                    {row.description}
                                                </TableCell>
                                                <TableCell align="right">{row.amount}</TableCell>
                                                <TableCell align="right">{row.dueDate}</TableCell>
                                                <TableCell align="right">{row.type}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </Container>
                    </div>
                </div>
            </div>
        </>
    )
}
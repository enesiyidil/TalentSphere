import styles from "../Css/Personal.module.css";
import {useDispatch, useSelector} from "react-redux";
import * as React from "react";
import {useContext, useState} from "react";
import TableRow from "@mui/material/TableRow";
import TableCell from "@mui/material/TableCell";
import IconButton from "@mui/material/IconButton";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp.js";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown.js";
import Collapse from "@mui/material/Collapse";
import Typography from "@mui/material/Typography";
import {MenuItem, Paper, Table, TableBody, TableContainer, TableHead} from "@mui/material";
import {
    API_GATEWAY_URL,
    DELETE_URL,
    GET_INFORMATION,
    MANAGER_URL,
    PERSONAL_URL,
    SAVE_URL,
    UPDATE_URL
} from "../constant/Endpoints.js";
import {setData} from "../redux/actions.js";
import {ApiContext} from "../context/ApiContext.jsx";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import {UserProfileContext} from "../context/UserProfileContext.jsx";

export default function Personal() {

    const data = useSelector((state) => state.data);
    const [personals, setPersonals] = useState(data.personals);
    const [index, setIndex] = useState(0);
    const {apiPatch, apiGet, apiPost, apiDelete} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const dispatch = useDispatch();
    const {handleSetUserProfile} = useContext(UserProfileContext);

    const updateData = async () => {
        const response = await apiGet(`${API_GATEWAY_URL}${MANAGER_URL}${GET_INFORMATION}?id=${userProfile.id}`, token);
        if (response.status === 200) {
            dispatch(setData(response.data));
            setPersonals(response.data.personals);
        } else {

        }
    }

    const handleUpdateClick = (updatedPersonal) => {
        const request = async () => {
            const response = await apiPatch(`${API_GATEWAY_URL}${PERSONAL_URL}${UPDATE_URL}`, {
                id: updatedPersonal.id,
                name: updatedPersonal.name,
                surname: updatedPersonal.surname,
                email: updatedPersonal.email,
                phone: updatedPersonal.phone,
                title: updatedPersonal.title,
                photo: updatedPersonal.photo,
                salary: updatedPersonal.salary,
                shiftId: updatedPersonal.shiftId,
                companyId: data.id,
                managerId: data.managerId
            }, token);
            if (response.status === 200) {
                await updateData();
            } else {

            }
        }
        request()

    };
    const handleSaveClick = (personal) => {
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${PERSONAL_URL}${SAVE_URL}`, {
                name: personal.name,
                surname: personal.surname,
                email: personal.email,
                phone: personal.phone,
                title: personal.title,
                photo: personal.photo,
                salary: personal.salary,
                shiftId: personal.shiftId,
                companyId: data.id,
                managerId: data.managerId,
                gender: personal.gender
            }, token);
            if (response.status === 200) {
                // dispatch(addPersonal(response.data));
                // setPersonals(prevState => ([...prevState.filter(item => item.email !== personal.email), response.data]));
                await updateData();
                handleSetUserProfile();
            } else {

            }
        }
        request()

    };

    const handleDeleteClick = (personal) => {
        if (personal.hasOwnProperty("id")) {
            const request = async () => {
                const response = await apiDelete(`${API_GATEWAY_URL}${PERSONAL_URL}${DELETE_URL}/${personal.id}`);
                if (response.status === 200) {
                    setPersonals(prevState => prevState.filter(item => item.id !== personal.id));
                    dispatch(setData({...data, personals: personals}));
                    handleSetUserProfile();
                } else {
                    alert('Failed!!');
                }
            }
            request();
        } else {
            setPersonals(prevState => prevState.filter(item => item.hasOwnProperty("id") || item.index !== personal.index));
        }
    }

    return (
        <>
            <div style={{width: '90%', margin: 'auto'}}>
                <TableContainer component={Paper} style={{backgroundColor: "rgba(26, 26, 26, 0.75)",}}>
                    <Table aria-label="collapsible table">
                        <TableHead>
                            <TableRow>
                                <TableCell/>
                                <TableCell></TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>Title</TableCell>
                                <TableCell align="right"
                                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>First
                                    Name</TableCell>
                                <TableCell align="right"
                                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>Last
                                    Name</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>E-Mail</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>Phone</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>Salary</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>Update/Delete</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {Array.isArray(personals) && personals.map(personal => <Row key={personal.id}
                                                                                        row={personal} data={data}
                                                                                        handleUpdateClick={handleUpdateClick}
                                                                                        handleDeleteClick={handleDeleteClick}
                                                                                        handleSaveClick={handleSaveClick}/>)}
                            <TableRow>
                                <button onClick={e => {
                                    setPersonals(prevState => ([...prevState, {
                                        index: index,
                                        shiftId: data.shifts.length > 0 ? data.shifts[0] : 0,
                                        companyId: data.companyId,
                                        managerId: data.managerId,
                                        name: "",
                                        surname: "",
                                        email: "",
                                        phone: "",
                                        title: "",
                                        photo: "",
                                        salary: 0,
                                        gender: "NO_GENDER"
                                    }]));
                                    setIndex(prevState => prevState + 1);
                                }} type="button" className={styles["button"]}>
                                    Add Personal
                                </button>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            </div>
            {/*{data.personals && data.personals.map(personal =>*/}
            {/*    <PersonalCard key={personal.id} personal={personal} editing={false}/>)}*/}
            {/*{addPersonal && <PersonalCard key={""} personal={{}} editing={true} setAddPersonal={setAddPersonal}/>}*/}
            {/*<div className={styles["button"]}>*/}
            {/*    <button onClick={() => setAddPersonal(true)} type="button" className={styles["button"]}>*/}
            {/*        Add Personal*/}
            {/*    </button>*/}
            {/*</div>*/}

        </>
    )
}

function Row(props) {
    const {row, data, handleUpdateClick, handleDeleteClick, handleSaveClick} = props;
    const [open, setOpen] = React.useState(false);
    const [updatedPersonal, setUpdatedPersonal] = React.useState(row.hasOwnProperty("id") ? "" : row);
    console.log(updatedPersonal)

    const shift = data.shifts.filter(shift => shift.id === row.shiftId)[0];

    return (

        <React.Fragment>

            <TableRow sx={{'& > *': {borderBottom: 'unset'}}} style={updatedPersonal ? {
                backgroundColor: "rgb(47,84,29",
                fontWeight: "550",
                fontSize: "medium",
                color: "rgb(0,252,252)",
            } : {}}>
                <TableCell>
                    <IconButton
                        style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        aria-label="expand row"
                        size="small"
                        onClick={() => setOpen(!open)}
                    >
                        {open ? <KeyboardArrowUpIcon/> : <KeyboardArrowDownIcon/>}
                    </IconButton>
                </TableCell>
                <TableCell align="right">{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="photo"
                        label="Photo"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, photo: e.target.value}))}
                        defaultValue={updatedPersonal.photo}
                    />
                    : <img src={row.photo} alt={""}/>}</TableCell>
                <TableCell align="right"
                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="title"
                        label="Title"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, title: e.target.value}))}
                        defaultValue={updatedPersonal.title}
                    />
                    : row.title}</TableCell>
                <TableCell align="right"
                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="name"
                        label="Name"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, name: e.target.value}))}
                        defaultValue={updatedPersonal.name}
                    />
                    : row.name}</TableCell>
                <TableCell align="right"
                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="surname"
                        label="Surname"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, surname: e.target.value}))}
                        defaultValue={updatedPersonal.surname}
                    />
                    : row.surname}</TableCell>
                <TableCell align="right"
                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="email"
                        label="E-Mail"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, email: e.target.value}))}
                        defaultValue={updatedPersonal.email}
                    />
                    : row.email}</TableCell>
                <TableCell align="right"
                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="phone"
                        label="Phone"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, phone: e.target.value}))}
                        defaultValue={updatedPersonal.phone}
                    />
                    : row.phone}</TableCell>
                <TableCell align="right"
                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>{updatedPersonal ?
                    <TextField
                        style={{backgroundColor: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}
                        required
                        id="salary"
                        label="Salary"
                        color={"secondary"}
                        type="number"
                        onChange={e => setUpdatedPersonal(prevState => ({...prevState, salary: e.target.value}))}
                        defaultValue={updatedPersonal.salary}
                    />
                    : row.salary}</TableCell>
                <TableCell align="right">
                    <div className={styles["button-wrapper"]}>
                        <button onClick={e => {
                            if (updatedPersonal) {
                                if (row.hasOwnProperty("id")) {
                                    handleUpdateClick(updatedPersonal);
                                } else {
                                    handleSaveClick(updatedPersonal);
                                }
                                setUpdatedPersonal("");
                            } else {
                                setUpdatedPersonal(row);
                            }
                        }} type="button" className={styles["button"]} value={row.id}>
                            {updatedPersonal ? "Save" : "Update"}
                        </button>
                        <button type='button' className={styles["button"]} onClick={() => {
                            if (updatedPersonal) {
                                setUpdatedPersonal("");
                                if (!row.hasOwnProperty("id"))
                                    handleDeleteClick(row);
                            } else {
                                handleDeleteClick(row);
                            }
                        }}
                                value={row.id}>{updatedPersonal ? "Cancel" : "Delete"}
                        </button>
                    </div>
                </TableCell>
            </TableRow>
            <TableRow style={updatedPersonal ? {
                backgroundColor: "rgb(47,84,29)",
                fontWeight: "550",
                fontSize: "medium",
                color: "rgb(0,252,252)",
                width: "100%"
            } : {}}>
                <TableCell style={{paddingBottom: 0, paddingTop: 0}} colSpan={9}>
                    <Collapse in={open} timeout="auto" unmountOnExit>
                        <Typography variant="h6" gutterBottom component="div"
                                    style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>
                            Detail
                        </Typography>
                        <TableHead>
                            <TableRow>
                                <TableCell></TableCell>
                                <TableCell style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>Shift</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}></TableCell>
                                <TableCell align="right"
                                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>Comment
                                    Number</TableCell>
                                <TableCell align="right"
                                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>Updated
                                    Date</TableCell>
                                <TableCell align="right"
                                           style={{color: "rgb(246,250,244)", fontWeight: "550", fontSize: "larger"}}>Created
                                    Date</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            <TableRow>
                                {updatedPersonal ?
                                    <>
                                        <Select
                                            style={{
                                                backgroundColor: "rgb(246,250,244)",
                                                fontWeight: "550",
                                                fontSize: "medium"
                                            }}
                                            id="shift-select"
                                            value={updatedPersonal.shiftId}
                                            label="Shift"
                                            onChange={e => setUpdatedPersonal(prevState => ({
                                                ...prevState,
                                                shiftId: e.target.value
                                            }))}
                                        >
                                            {data.shifts.map(shift =>
                                                <MenuItem value={shift.id}>{shift.name}</MenuItem>)}
                                        </Select>
                                        {!updatedPersonal.hasOwnProperty("id") &&
                                            <Select
                                                style={{
                                                    backgroundColor: "rgb(246,250,244)",
                                                    fontWeight: "550",
                                                    fontSize: "medium"
                                                }}
                                                id="gender"
                                                label="Gender"
                                                value={updatedPersonal.gender}
                                                onChange={e => setUpdatedPersonal(prevState => ({
                                                    ...prevState,
                                                    gender: e.target.value
                                                }))}
                                            >
                                                <MenuItem value="MAN">MAN</MenuItem>
                                                <MenuItem value="WOMAN">WOMAN</MenuItem>
                                                <MenuItem value="NO_GENDER">NO_GENDER</MenuItem>
                                            </Select>}
                                    </>
                                    : <>
                                        <TableCell align="right" style={{
                                            color: "rgb(246,250,244)",
                                            fontWeight: "550",
                                            fontSize: "larger"
                                        }}>{shift && shift.name}</TableCell>
                                        <TableCell
                                            align="right" style={{
                                            color: "rgb(246,250,244)",
                                            fontWeight: "550",
                                            fontSize: "larger"
                                        }}>({shift && shift.startTime} - {shift && shift.endTime})</TableCell>
                                    </>}
                                <TableCell align="right"></TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>{row.comment && row.comment.length}</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>{row.updatedDate}</TableCell>
                                <TableCell align="right" style={{
                                    color: "rgb(246,250,244)",
                                    fontWeight: "550",
                                    fontSize: "larger"
                                }}>{row.createdDate}</TableCell>
                            </TableRow>
                        </TableBody>
                    </Collapse>
                </TableCell>

            </TableRow>

        </React.Fragment>

    );
}

import * as React from 'react';
import {useContext, useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import styles from "../Css/AddCompany.module.css";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {API_GATEWAY_URL, COMPANY_URL, SAVE_URL} from "../constant/Endpoints.js";
import "../Css/AddCompany.module.css"
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

export default function AddCompany() {

    const {apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const navigate = useNavigate();

    const [company, setCompany] = useState({
        name: "",
        address: "",
        communicationName: "",
        communicationPhone: "",
        gallery: [""],
        shifts: [
            {
                name: "",
                startTime: "",
                endTime: "",
                breaks: [
                    {
                        name: "",
                        startTime: "",
                        endTime: ""
                    }
                ]
            }
        ]
    })

    const handleClearClick = () => {
        setCompany({
            name: "",
            address: "",
            communicationName: "",
            communicationPhone: "",
            gallery: [""],
            shifts: [
                {
                    name: "",
                    startTime: "",
                    endTime: "",
                    breaks: [
                        {
                            name: "",
                            startTime: "",
                            endTime: ""
                        }
                    ]
                }
            ]
        })
    }

    const handleSaveClick = () => {
        apiPost(`${API_GATEWAY_URL}${COMPANY_URL}${SAVE_URL}`, company, token);
        handleClearClick();
        navigate('/home');
    }

    return (
        <>
            <div className={styles["formWrapper"]}>
                <Box
                    component="form"
                    sx={{
                        '& .MuiTextField-root': {m: 1, width: '25ch', backgroundColor: '#FFFFFF'},
                    }}
                    noValidate
                    autoComplete="off"
                >
                    <div>
                        <TextField
                            required
                            id="name"
                            label="Company Name"
                            onChange={e => setCompany(prevState => ({...prevState, name: e.target.value}))}
                            defaultValue={company.name}
                        />
                        <TextField
                            required
                            id="address"
                            label="Address"
                            onChange={e => setCompany(prevState => ({...prevState, address: e.target.value}))}
                            defaultValue={company.address}
                        />
                    </div>
                    <div>
                        <TextField
                            required
                            id="communication-name"
                            label="Communication Name"
                            onChange={e => setCompany(prevState => ({...prevState, communicationName: e.target.value}))}
                            defaultValue={company.communicationName}
                        />
                        <TextField
                            required
                            id="communication-phone"
                            label="Communication Phone"
                            onChange={e => setCompany(prevState => ({
                                ...prevState,
                                communicationPhone: e.target.value
                            }))}
                            defaultValue={company.communicationPhone}
                        />
                    </div>
                    <div>
                        <TableContainer component={Paper}>
                            <Table sx={{minWidth: 650}} aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Gallery</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {company.gallery.map((row, index) => (
                                        <TableRow
                                            key={index}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell component="th" scope="row">
                                                <TextField
                                                    required
                                                    id={`photo-url-${index}`} // Shift ismini benzersiz bir ID ile belirtin
                                                    label="Photo URL"
                                                    onChange={e => setCompany(prevState => ({
                                                        ...prevState,
                                                        gallery: prevState.gallery.map((photo, i) =>
                                                            index === i ? e.target.value : photo
                                                        )
                                                    }))}
                                                    defaultValue={row.name}
                                                />
                                            </TableCell>
                                            <TableCell align="right">
                                                {row && (
                                                    <img
                                                        src={row}
                                                        alt="Uploaded"
                                                        style={{maxWidth: '100px', maxHeight: '100px', marginTop: '10px'}}
                                                    />
                                                )}
                                            </TableCell>

                                            {company.gallery.length > 1 && <TableCell align="right">
                                                <button type="button" onClick={() => {
                                                    setCompany(prevState => ({
                                                        ...prevState,
                                                        gallery: prevState.gallery.filter((_, i) => i !== index)
                                                    }));
                                                }}>Delete Photo
                                                </button>
                                            </TableCell>}
                                        </TableRow>
                                    ))}
                                    <TableRow key={"new-photo"} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                        <button type={"button"} onClick={(e) => {

                                            setCompany(prevState => ({
                                                ...prevState,
                                                gallery: [
                                                    ...prevState.gallery,
                                                    ""
                                                ]
                                            }));
                                        }}>Add Photo
                                        </button>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>
                    <div>
                        <TableContainer component={Paper}>
                            <Table sx={{minWidth: 650}} aria-label="simple table">
                                <TableHead>
                                    <TableRow>
                                        <TableCell>Shift Name</TableCell>
                                        <TableCell align="right">Start Time</TableCell>
                                        <TableCell align="right">End Time</TableCell>
                                        <TableCell align="right">Breaks</TableCell>
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {company.shifts.map((row, index) => (
                                        <TableRow
                                            key={row.name}
                                            sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                        >
                                            <TableCell component="th" scope="row">
                                                <TextField
                                                    required
                                                    id={`shift-name-${index}`} // Shift ismini benzersiz bir ID ile belirtin
                                                    label="Name"
                                                    onChange={e => setCompany(prevState => ({
                                                        ...prevState,
                                                        shifts: prevState.shifts.map((shift, i) =>
                                                            index === i ? {...shift, name: e.target.value} : shift
                                                        )
                                                    }))}
                                                    defaultValue={row.name}
                                                />
                                            </TableCell>
                                            <TableCell align="right">
                                                <TextField
                                                    required
                                                    id={`shift-start-time-${index}`} // Başlangıç saati için benzersiz bir ID
                                                    label="Start Time"
                                                    type="time"
                                                    onChange={e => setCompany(prevState => ({
                                                        ...prevState,
                                                        shifts: prevState.shifts.map((shift, i) =>
                                                            index === i ? {...shift, startTime: e.target.value} : shift
                                                        )
                                                    }))}
                                                    defaultValue={row.startTime}
                                                />
                                            </TableCell>
                                            <TableCell align="right">
                                                <TextField
                                                    required
                                                    id={`shift-end-time-${index}`} // Bitiş saati için benzersiz bir ID
                                                    label="End Time"
                                                    type="time"
                                                    onChange={e => setCompany(prevState => ({
                                                        ...prevState,
                                                        shifts: prevState.shifts.map((shift, i) =>
                                                            index === i ? {...shift, endTime: e.target.value} : shift
                                                        )
                                                    }))}
                                                    defaultValue={row.endTime}
                                                />
                                            </TableCell>
                                            <TableCell align="right"><TableContainer component={Paper}>
                                                <Table sx={{minWidth: 650}} aria-label="simple table">
                                                    <TableHead>
                                                        <TableRow>
                                                            <TableCell>Break Name</TableCell>
                                                            <TableCell align="right">Start Time</TableCell>
                                                            <TableCell align="right">End Time</TableCell>
                                                        </TableRow>
                                                    </TableHead>
                                                    <TableBody>
                                                        {row.breaks.map((breakItem, breakIndex) => (
                                                            <TableRow
                                                                key={breakIndex}
                                                                sx={{'&:last-child td, &:last-child th': {border: 0}}}
                                                            >
                                                                <TableCell component="th" scope="row">
                                                                    <TextField
                                                                        required
                                                                        id={`shift-${index}-break-name-${breakIndex}`} // Mola ismi için benzersiz bir ID
                                                                        label="Break Name"
                                                                        onChange={e => setCompany(prevState => ({
                                                                            ...prevState,
                                                                            shifts: prevState.shifts.map((shift, i) =>
                                                                                index === i ? {
                                                                                        ...shift,
                                                                                        breaks: shift.breaks.map((b, j) =>
                                                                                            breakIndex === j ? {
                                                                                                ...b,
                                                                                                name: e.target.value
                                                                                            } : b
                                                                                        )
                                                                                    }
                                                                                    : shift
                                                                            )
                                                                        }))}
                                                                        defaultValue={breakItem.name}
                                                                    />
                                                                </TableCell>
                                                                <TableCell align="right">
                                                                    <TextField
                                                                        required
                                                                        id={`shift-${index}-break-start-time-${breakIndex}`} // Mola başlangıç saati için benzersiz bir ID
                                                                        label="Start Time"
                                                                        type="time"
                                                                        onChange={e => setCompany(prevState => ({
                                                                            ...prevState,
                                                                            shifts: prevState.shifts.map((shift, i) =>
                                                                                index === i ? {
                                                                                        ...shift,
                                                                                        breaks: shift.breaks.map((b, j) =>
                                                                                            breakIndex === j ? {
                                                                                                ...b,
                                                                                                startTime: e.target.value
                                                                                            } : b
                                                                                        )
                                                                                    }
                                                                                    : shift
                                                                            )
                                                                        }))}
                                                                        defaultValue={breakItem.startTime}
                                                                    />
                                                                </TableCell>
                                                                <TableCell align="right">
                                                                    <TextField
                                                                        required
                                                                        id={`shift-${index}-break-end-time-${breakIndex}`} // Mola bitiş saati için benzersiz bir ID
                                                                        label="End Time"
                                                                        type="time"
                                                                        onChange={e => setCompany(prevState => ({
                                                                            ...prevState,
                                                                            shifts: prevState.shifts.map((shift, i) =>
                                                                                index === i ? {
                                                                                        ...shift,
                                                                                        breaks: shift.breaks.map((b, j) =>
                                                                                            breakIndex === j ? {
                                                                                                ...b,
                                                                                                endTime: e.target.value
                                                                                            } : b
                                                                                        )
                                                                                    }
                                                                                    : shift
                                                                            )
                                                                        }))}
                                                                        defaultValue={breakItem.endTime}
                                                                    />
                                                                </TableCell>
                                                                {row.breaks.length > 1 && <TableCell align="right">
                                                                    <button type="button" onClick={() => {
                                                                        setCompany(prevState => ({
                                                                            ...prevState,
                                                                            shifts: prevState.shifts.map((shift, i) =>
                                                                                index === i ? {
                                                                                        ...shift,
                                                                                        breaks: shift.breaks.filter((_, j) => j !== breakIndex) // İlgili mola filtrelenir ve silinir
                                                                                    }
                                                                                    : shift
                                                                            )
                                                                        }));
                                                                    }}>Delete Break
                                                                    </button>
                                                                </TableCell>}
                                                            </TableRow>
                                                        ))}
                                                        <TableRow key={"new"}
                                                                  sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                                            <button type={"button"} onClick={(e) => {

                                                                setCompany(prevState => ({
                                                                    ...prevState,
                                                                    shifts: prevState.shifts.map((shift, i) =>
                                                                        index === i ? {
                                                                                ...shift,
                                                                                breaks: [
                                                                                    ...shift.breaks,
                                                                                    {
                                                                                        name: "",
                                                                                        startTime: "",
                                                                                        endTime: ""
                                                                                    }
                                                                                ]
                                                                            }
                                                                            : shift
                                                                    )
                                                                }));
                                                            }}>Add Break
                                                            </button>
                                                        </TableRow>
                                                    </TableBody>
                                                </Table>
                                            </TableContainer></TableCell>
                                            {company.shifts.length > 1 && <TableCell align="right">
                                                <button type="button" onClick={() => {
                                                    setCompany(prevState => ({
                                                        ...prevState,
                                                        shifts: prevState.shifts.filter((_, i) => i !== index) // İlgili vardiya (shift) filtrelenir ve silinir
                                                    }));
                                                }}>Delete Shift
                                                </button>
                                            </TableCell>}
                                        </TableRow>
                                    ))}
                                    <TableRow key={"new-shift"} sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                        <button type={"button"} onClick={(e) => {

                                            setCompany(prevState => ({
                                                ...prevState,
                                                shifts: [
                                                    ...prevState.shifts, {
                                                        name: "",
                                                        startTime: "",
                                                        endTime: "",
                                                        breaks: [
                                                            {
                                                                name: "",
                                                                startTime: "",
                                                                endTime: ""
                                                            }
                                                        ]
                                                    }
                                                ]
                                            }));
                                        }}>Add Shift
                                        </button>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </TableContainer>
                    </div>
                </Box>
                <div className={styles["button"]}>
                    <button onClick={handleSaveClick} type="button" className={styles["button"]}>
                        Save
                    </button>
                    <button type='button' onClick={handleClearClick} className={styles["button"]}>Clear</button>
                </div>
            </div>
        </>
    )
        ;
}
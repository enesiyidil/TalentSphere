import * as React from 'react';
import {useContext, useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import styles from "../Css/ManagerAdd.module.css";
import Select from '@mui/material/Select';
import {MenuItem} from "@mui/material";
import {API_GATEWAY_URL, COMPANY_URL, FIND_ALL_WITHOUT_MANAGER, MANAGER_URL, SAVE_URL} from "../constant/Endpoints.js";


export default function AddManager() {

    const {apiPost, apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const navigate = useNavigate();
    const [companies, setCompanies] = useState([{name: "", id: 0, shifts: [{name: "", id: 0}]}])

    const [manager, setManager] = useState({
        name: "",
        surname: "",
        email: "",
        companyId: companies.length > 0 ? companies[0].id : 0,
        shiftId: companies.length > 0 ? companies[0].shifts[0].id : 0,
        role: "MANAGER",
        gender: "NO_GENDER",
        photo: "",
        title: "",
        salary: 0,
        packet: "P_30"
    });

    useEffect(() => {
        const request = async () => {
            const response = await apiGet(`${API_GATEWAY_URL}${COMPANY_URL}${FIND_ALL_WITHOUT_MANAGER}`, token);
            if (response.status === 200) {
                setCompanies(response.data);
            } else {
                handleClearClick();
                // navigate('/home');
            }
        }
        request()

    }, []);

    const handleClearClick = () => {
        setManager({
            name: "",
            surname: "",
            email: "",
            companyId: companies.length > 0 ? companies[0].id : 0,
            shiftId: companies.length > 0 ? companies[0].shifts[0].id : 0,
            role: "MANAGER",
            gender: "NO_GENDER",
            photo: "",
            title: "",
            salary: 0,
            packet: "P_30"
        })
    }

    const handleSaveClick = () => {
        apiPost(`${API_GATEWAY_URL}${MANAGER_URL}${SAVE_URL}`, manager, token);
        console.log("manager -----", manager)
        // handleClearClick();
        navigate('/home');
    }

    return (
        <>
            <div className={styles["manager-add"]}>


                <Box
                    component="form"
                    sx={{
                        '& .MuiTextField-root': {m: 1, width: '25ch', backgroundColor: 'white'},
                    }}
                    noValidate
                    autoComplete="off"
                >

                    <div>
                        {manager.photo && (
                            <img
                                src={manager.photo}
                                alt="Uploaded"
                                style={{maxWidth: '100px', maxHeight: '100px', marginTop: '10px'}}
                            />
                        )}
                        <TextField
                            required
                            id="photo"
                            label="Manager Photo"
                            onChange={e => setManager(prevState => ({...prevState, photo: e.target.value}))}
                            defaultValue={manager.photo}
                        />
                        <TextField
                            required
                            id="name"
                            label="Manager Name"
                            onChange={e => setManager(prevState => ({...prevState, name: e.target.value}))}
                            defaultValue={manager.name}
                        />
                        <TextField
                            required
                            id="surname"
                            label="Manager Surname"
                            onChange={e => setManager(prevState => ({...prevState, surname: e.target.value}))}
                            defaultValue={manager.surname}
                        />
                        <TextField
                            required
                            id="email"
                            label="Manager E-Mail"
                            onChange={e => setManager(prevState => ({...prevState, email: e.target.value}))}
                            defaultValue={manager.email}
                        />
                        <TextField
                            required
                            id="phone"
                            label="Manager Phone"
                            onChange={e => setManager(prevState => ({...prevState, phone: e.target.value}))}
                            defaultValue={manager.phone}
                        />
                        <TextField
                            required
                            id="title"
                            label="Manager Title"
                            onChange={e => setManager(prevState => ({...prevState, title: e.target.value}))}
                            defaultValue={manager.title}
                        />
                        <TextField
                            required
                            id="salary"
                            label="Salary"
                            type="number"
                            onChange={e => setManager(prevState => ({...prevState, salary: e.target.value}))}
                            value={manager.salary}
                        />
                        <Select  style={{backgroundColor: 'white'}}
                            id="gender"
                            label="Gender"
                            value={manager.gender}
                            onChange={e => setManager(prevState => ({...prevState, gender: e.target.value}))}
                        >
                            <MenuItem value="MAN">MAN</MenuItem>
                            <MenuItem value="WOMAN">WOMAN</MenuItem>
                            <MenuItem value="NO_GENDER">NO_GENDER</MenuItem>
                        </Select>
                        <Select  style={{backgroundColor: 'white'}}
                                 id="epackage"
                            label="Epackage"
                            value={manager.packet}
                            onChange={e => setManager(prevState => ({...prevState, packet: e.target.value}))}
                        >
                            <MenuItem value="P_30">P_30</MenuItem>
                            <MenuItem value="P_60">P_60</MenuItem>
                            <MenuItem value="P_90">P_90</MenuItem>
                        </Select>
                    </div>
                    <div className={styles["selector"]}>
                        <Select
                            id="company-select"
                            value={manager.companyId}
                            label="Company"
                            onChange={e => setManager(prevState => ({...prevState, companyId: e.target.value}))}
                        >
                            {companies.map(company => (<MenuItem value={company.id}>{company.name}</MenuItem>))}
                        </Select>
                        <Select
                            id="shift-select"
                            value={manager.shiftId}
                            label="Shift"
                            onChange={e => setManager(prevState => ({...prevState, shiftId: e.target.value}))}
                        >
                            {companies.map(company => (company.id === manager.companyId && company.shifts.map(shift =>
                                <MenuItem value={shift.id}>{shift.name}</MenuItem>)))}
                        </Select>
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
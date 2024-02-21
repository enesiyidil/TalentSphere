import * as React from 'react';
import {useContext, useState} from 'react';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import styles from "../Css/AddCompany.module.css";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {API_GATEWAY_URL, COMPANY_URL, SAVE_URL} from "../constant/Endpoints.js";

export default function AddCompany() {

    const {apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const navigate = useNavigate();

    const [company, setCompany] = useState({
        name: "",
        address: "",
        communicationName: "",
        communicationPhone: "",
    })

    const handleClearClick = () => {
        setCompany({
            name: "",
            address: "",
            communicationName: "",
            communicationPhone: "",
        })
    }

    const handleSaveClick = () => {
        apiPost(`${API_GATEWAY_URL}${COMPANY_URL}${SAVE_URL}`, company, token);
        handleClearClick();
        navigate('/home');
    }

    return (
        <>
            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': {m: 1, width: '25ch'},
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
                        onChange={e => setCompany(prevState => ({...prevState, communicationPhone: e.target.value}))}
                        defaultValue={company.communicationPhone}
                    />
                </div>
            </Box>
            <div className={styles["button-wrapper"]}>
                <button onClick={handleSaveClick} type="button" className={styles["button"]}>
                    Save
                </button>
                <button type='button' onClick={handleClearClick} className={styles["button"]}>Clear</button>
            </div>
        </>
    )
        ;
}
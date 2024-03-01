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
    const [companies, setCompanies] = useState([{name: "", id: 0}])

    const [manager, setManager] = useState({
        name: "",
        surname: "",
        email: "",
        companyId: ""
    });

    useEffect(() => {
        const request = async () =>{
            const response = await apiGet(`${API_GATEWAY_URL}${COMPANY_URL}${FIND_ALL_WITHOUT_MANAGER}`, token);
            if(response.status === 200){
                setCompanies(response.data);
            }else{
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
            companyId: ""
        })
    }

    const handleSaveClick = () => {
        apiPost(`${API_GATEWAY_URL}${MANAGER_URL}${SAVE_URL}`,manager, token);
        console.log("manager -----",manager)
        // handleClearClick();
        navigate('/home');
    }

    return (
        <>
            <div  className={styles["manager-add"]}>



            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': {m: 1, width: '25ch', backgroundColor:'white' },
                }}
                noValidate
                autoComplete="off"
            >

                <div>
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
                </div>
                <div className={styles["selector"]}>
                    <Select
                        id="select"
                        value={manager.companyId}
                        label="Company"
                        onChange={e => setManager(prevState => ({...prevState, companyId: e.target.value}))}
                    >
                        {companies.map(company => (<MenuItem value={company.id}>{company.name}</MenuItem>))}
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
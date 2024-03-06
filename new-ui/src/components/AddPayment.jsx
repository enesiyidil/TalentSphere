import styles from "../Css/AddPayment.module.css";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {
    API_GATEWAY_URL,
    COMPANY_URL,
    FIND_ALL_WITHOUT_MANAGER,
    MANAGER_URL,
    PAYMENT_URL,
    SAVE_URL
} from "../constant/Endpoints.js";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import {MenuItem} from "@mui/material";
import * as React from "react";
import FormControl from '@mui/material/FormControl';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import dayjs from "dayjs";

export default function AddPayment () {
    const {apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const role = useSelector((state) => state.role);
    const authId = useSelector((state) => state.authId);
    const navigate = useNavigate();
    const [payment, setPayment] = useState({
        amount: 0,
        companyId: userProfile.companyId,
        authId: authId,
        dueDate: dayjs(),
        description: "",
        expenditureType: "",
        role: role,
        currency: "",
        type: ""
    });

    const handleClearClick = () => {
        setPayment({
            amount: 0,
            companyId: userProfile.companyId,
            authId: authId,
            dueDate: dayjs(),
            description: "",
            expenditureType: "",
            role: role,
            currency: "",
            type: ""
        })
    }

    const handleSaveClick = () => {
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${PAYMENT_URL}${SAVE_URL}`, payment, token);
            if(response.status === 200){

            }else {
                alert(response.data.message);
            }
            navigate('/home');
        }
        request();
    };

    const handleDueDateChange = (event) => {
        const date = dayjs(event.target.value);
        setPayment(prevState => ({...prevState, dueDate: date.isValid() ? date : dayjs()}));
    };

    return (
        <>
            <div className={styles["payment-add"]}>

                <Box
                    component="form"
                    sx={{
                        '& .MuiTextField-root': {m: 1, width: '25ch', backgroundColor: 'white'},
                    }}
                    noValidate
                    autoComplete="off"
                >

                    <div>
                        <TextField
                            required
                            id="expenditureType"
                            label="Expenditure Type"
                            onChange={e => setPayment(prevState => ({...prevState, expenditureType: e.target.value}))}
                            defaultValue={payment.expenditureType}
                        />
                        <Select
                            id="type"
                            label="Type"
                            value={payment.type}
                            onChange={e => setPayment(prevState => ({...prevState, type: e.target.value}))}
                        >
                            <MenuItem value="INCOME">INCOME</MenuItem>
                            <MenuItem value="EXPENSE">EXPENSE</MenuItem>
                        </Select>
                        <TextField
                            required
                            id="description"
                            label="Description"
                            onChange={e => setPayment(prevState => ({...prevState, description: e.target.value}))}
                            defaultValue={payment.description}
                        />
                        <Select
                            id="gender"
                            label="Gender"
                            value={payment.currency}
                            onChange={e => setPayment(prevState => ({...prevState, currency: e.target.value}))}
                        >
                            <MenuItem value="TL">TL</MenuItem>
                            <MenuItem value="USD">USD</MenuItem>
                            <MenuItem value="EUR">EUR</MenuItem>
                        </Select>
                        <FormControl fullWidth sx={{ m: 1 }}>
                            <InputLabel htmlFor="outlined-adornment-amount">Amount</InputLabel>
                            <OutlinedInput
                                id="outlined-adornment-amount"
                                startAdornment={<InputAdornment position="start">{payment.currency}</InputAdornment>}
                                label="Amount"
                                value={payment.amount}
                                onChange={(e) => setPayment(prevState => ({...prevState, amount: e.target.value}))}
                            />
                        </FormControl>
                        <div>
                            <label htmlFor="due-date" className="flex text-md font-medium text-gray-700">
                                Due Date
                            </label>
                            <input
                                type="date"
                                id="due-date"
                                name="due-date"
                                value={payment.dueDate.format('YYYY-MM-DD')}
                                onChange={handleDueDateChange}
                                className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                            />
                        </div>
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
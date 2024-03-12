import styles from "../Css/AddPayment.module.css";
import * as React from "react";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import {API_GATEWAY_URL, GET_INFORMATION, MANAGER_URL, PAYMENT_URL, SAVE_URL} from "../constant/Endpoints.js";
import Box from "@mui/material/Box";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import {MenuItem} from "@mui/material";
import FormControl from '@mui/material/FormControl';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import dayjs from "dayjs";
import {BarChart} from '@mui/x-charts/BarChart';
import {axisClasses, DEFAULT_Y_AXIS_KEY} from "@mui/x-charts";
import {setData} from "../redux/actions.js";

const chartSetting = {
    yAxis: [
        {
            label: 'Amount (TL)',
            width: 100
        },
    ],
    width: 700,
    height: 500,
    sx: {
        [`.${axisClasses.left} .${axisClasses.label}`]: {
            transform: 'translate(-20px, 0)',
        },
    },
};

const valueFormatter = (value) => `${value}TL`;
export default function AddPayment() {
    const {apiPost, apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const role = useSelector((state) => state.role);
    const authId = useSelector((state) => state.authId);
    const data = useSelector((state) => state.data);
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
    const [dataset, setDataset] = useState([
        {
            income: 0,
            expense: 0,
            month: "Jan"
        },
        {
            income: 0,
            expense: 0,
            month: "Feb"
        },
        {
            income: 0,
            expense: 0,
            month: "Mar"
        },
        {
            income: 0,
            expense: 0,
            month: "Apr"
        },
        {
            income: 0,
            expense: 0,
            month: "May"
        },
        {
            income: 0,
            expense: 0,
            month: "Jun"
        },
        {
            income: 0,
            expense: 0,
            month: "Jul"
        },
        {
            income: 0,
            expense: 0,
            month: "Agu"
        },
        {
            income: 0,
            expense: 0,
            month: "Sep"
        },
        {
            income: 0,
            expense: 0,
            month: "Oct"
        },
        {
            income: 0,
            expense: 0,
            month: "Nov"
        },
        {
            income: 0,
            expense: 0,
            month: "Dec"
        }]);
    const dispatch = useDispatch();

    useEffect(() => {
        setDataset([
            {
                income: 0,
                expense: 0,
                month: "Jan"
            },
            {
                income: 0,
                expense: 0,
                month: "Feb"
            },
            {
                income: 0,
                expense: 0,
                month: "Mar"
            },
            {
                income: 0,
                expense: 0,
                month: "Apr"
            },
            {
                income: 0,
                expense: 0,
                month: "May"
            },
            {
                income: 0,
                expense: 0,
                month: "Jun"
            },
            {
                income: 0,
                expense: 0,
                month: "Jul"
            },
            {
                income: 0,
                expense: 0,
                month: "Agu"
            },
            {
                income: 0,
                expense: 0,
                month: "Sep"
            },
            {
                income: 0,
                expense: 0,
                month: "Oct"
            },
            {
                income: 0,
                expense: 0,
                month: "Nov"
            },
            {
                income: 0,
                expense: 0,
                month: "Dec"
            }]);
        if (role === "MANAGER") {
            data.payments.forEach(payment => {
                setDataset(prevState => {
                    const months = [
                        {item1: "01", item2: "Jan"},
                        {item1: "02", item2: "Feb"},
                        {item1: "03", item2: "Mar"},
                        {item1: "04", item2: "Apr"},
                        {item1: "05", item2: "May"},
                        {item1: "06", item2: "Jun"},
                        {item1: "07", item2: "Jul"},
                        {item1: "08", item2: "Aug"},
                        {item1: "09", item2: "Sep"},
                        {item1: "10", item2: "Oct"},
                        {item1: "11", item2: "Nov"},
                        {item1: "12", item2: "Dec"}
                    ].filter(month => month.item1 === payment.dueDate.substring(5, 7))[0].item2;
                    return prevState.map(item => item.month === months ? payment.type === "INCOME" ? {
                        ...item,
                        income: item.income + payment.amount
                    } : {...item, expense: item.expense + payment.amount} : item)

                })
            })
        }
    }, role === 'MANAGER' ? [data.payments.length] : []);

    const updateData = async () => {
        const response = await apiGet(`${API_GATEWAY_URL}${MANAGER_URL}${GET_INFORMATION}?id=${userProfile.id}`, token);
        if (response.status === 200) {
            dispatch(setData(response.data));
        } else {

        }
    }

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
            if (response.status === 200) {
                if(role === 'MANAGER')
                    await updateData();
                else
                    navigate("/home");
            } else {
                alert(response.data.message);
            }
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
                            style={{backgroundColor: 'white', top: '0.5rem'}}
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
                            style={{backgroundColor: 'white', top: '0.5rem'}}
                            onChange={e => setPayment(prevState => ({...prevState, currency: e.target.value}))}
                        >
                            <MenuItem value="TL">TL</MenuItem>
                            <MenuItem value="USD">USD</MenuItem>
                            <MenuItem value="EUR">EUR</MenuItem>
                        </Select>
                        <FormControl fullWidth sx={{m: 1}}>
                            <InputLabel htmlFor="outlined-adornment-amount">Amount</InputLabel>
                            <OutlinedInput
                                id="outlined-adornment-amount"
                                startAdornment={<InputAdornment position="start">{payment.currency}</InputAdornment>}
                                label="Amount"
                                style={{width: '70%', backgroundColor: 'white'}}
                                value={payment.amount}
                                onChange={(e) => setPayment(prevState => ({...prevState, amount: e.target.value}))}
                            />
                        </FormControl>
                        <div>
                            <label htmlFor="due-date" className="flex text-md font-medium text-gray-700"
                                   style={{fontWeight: '500', color: 'white', padding: '0.5rem'}}>
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
            {
                role === "MANAGER" && <div style={{
                    width: '45%',
                    backgroundColor: 'lightgrey',
                    padding: '1.5rem',
                    margin: "auto",
                    marginTop: '2rem'
                }}>
                    <BarChart
                        dataset={dataset}
                        xAxis={[{scaleType: 'band', dataKey: 'month'}]}
                        series={[
                            {dataKey: 'income', label: 'Income', valueFormatter, color: "green"},
                            {dataKey: 'expense', label: 'Expense', valueFormatter, color: "red"},
                        ]}
                        {...chartSetting}
                    />
                </div>}

        </>
    )
        ;
}
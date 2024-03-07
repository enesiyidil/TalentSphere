import React, {useContext, useEffect, useState} from 'react';
import {Calendar, momentLocalizer} from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import {useDispatch, useSelector} from "react-redux";
import {
    API_GATEWAY_URL,
    FIND_ALL_BY_PERSONAL_ID_URL,
    HOLIDAY_URL,
    SAVE_BY_PERSONAL,
    SAVE_URL
} from "../constant/Endpoints.js";
import {ApiContext} from "../context/ApiContext.jsx";
import dayjs from 'dayjs';
import 'dayjs/locale/tr';
import styles from "../Css/MyCalendar.module.css"; //mycalendar.module.css
import {addHoliday} from "../redux/actions.js";
import {DataGrid} from '@mui/x-data-grid';

const columns = [
    {field: 'id', headerName: 'ID', width: 70},
    {field: 'name', headerName: 'First name', width: 130},
    {field: 'surname', headerName: 'Last name', width: 130},
];
const DateRangePicker = ({personals, setPersonals}) => {
    const [startDate, setStartDate] = useState(dayjs());
    const [endDate, setEndDate] = useState(dayjs());
    const [description, setDescription] = useState('');
    const [name, setName] = useState('');

    const {apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const role = useSelector((state) => state.role);
    const data = useSelector((state) => state.data);
    const dispatch = useDispatch();

    const handleStartDateChange = (event) => {
        const date = dayjs(event.target.value);
        setStartDate(date.isValid() ? date : dayjs());
    };

    const handleEndDateChange = (event) => {
        const date = dayjs(event.target.value);
        setEndDate(date.isValid() ? date : dayjs());
    };

    const handleDescriptionChange = (event) => {
        setDescription(event.target.value);
    };

    const handleNameChange = (event) => {
        setName(event.target.value);
    };

    const handleClear = () => {
        setName('');
        setDescription('');
        setStartDate(dayjs());
        setEndDate(dayjs());
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        const response = role === 'MANAGER' ? await apiPost(`${API_GATEWAY_URL}${HOLIDAY_URL}${SAVE_URL}`, {
            name: name,
            description: description,
            startDate: startDate,
            endDate: endDate,
            personals: personals,
            companyId: userProfile.companyId,
            role
        }, token) : await apiPost(`${API_GATEWAY_URL}${HOLIDAY_URL}${SAVE_BY_PERSONAL}`, {
            name: name,
            description: description,
            startDate: startDate,
            endDate: endDate,
            personalId: userProfile.id,
            companyId: userProfile.companyId
        }, token);
        if (response.status === 200) {
            handleClear();
            if (role === 'MANAGER') {
                dispatch(addHoliday(response.data));
            }
        } else {
            alert('Connection Error')
        }
    };

    return (
        <div className={styles["wrapper"]}>
            <div className={styles["datepicker-wrapper"]} onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="start-date" className="flex text-md font-medium text-gray-700">
                        Start Date
                    </label>
                    <input
                        type="date"
                        id="start-date"
                        name="start-date"
                        value={startDate.format('YYYY-MM-DD')}
                        onChange={handleStartDateChange}
                        className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                </div>
                <div>
                    <label htmlFor="end-date" className="block text-sm font-medium text-gray-700">
                        End Date
                    </label>
                    <input

                        type="date"
                        id="end-date"
                        name="end-date"
                        value={endDate.format('YYYY-MM-DD')}
                        onChange={handleEndDateChange}
                        className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                </div>
                <div>
                    <label htmlFor="description" className="block text-sm font-medium text-gray-700">
                        Name
                    </label>
                    <input
                        type="text"
                        id="namae"
                        name="name"
                        value={name}
                        onChange={handleNameChange}
                        className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                </div>
                <div>
                    <label htmlFor="description" className="block text-sm font-medium text-gray-700">
                        Description
                    </label>
                    <input
                        type="text"
                        id="description"
                        name="description"
                        value={description}
                        onChange={handleDescriptionChange}
                        className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                </div>
            </div>
            {role === 'MANAGER' &&
                <div style={{height: 200, width: '100%', backgroundColor: 'white', marginTop: "10px"}}>
                    <DataGrid

                        rows={data.personals}
                        columns={columns}
                        initialState={{
                            pagination: {
                                paginationModel: {page: 0, pageSize: 5},
                            },
                        }}
                        pageSizeOptions={[5, 10]}
                        checkboxSelection
                        onStateChange={(e) => setPersonals(data.personals.filter(personal => e.rowSelection.includes(personal.id)).map(personal => personal.id))}
                    />
                </div>}
            <div style={{display: 'flex', flexDirection: 'row', paddingTop: '5px', marginTop: '20px'}}>
                <button onClick={handleSubmit} style={{backgroundColor: "lightgrey"}} type="button" className={"button"}
                >
                    Submit
                </button>
                <button type='button' style={{backgroundColor: "lightgrey"}} onClick={handleClear} className={"button"}
                >Clear
                </button>
            </div>
        </div>
    );
};


const myLocalizer = momentLocalizer(moment);

const MyCalendar = () => {
    const [personals, setPersonals] = useState([]);
    const data = useSelector((state) => state.data);
    const role = useSelector((state) => state.role);
    const {apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const [selected, setSelected] = useState([]);
    const [holidays, setHolidays] = useState([{
        name: "",
        id: 0,
        description: "",
        startDate: 0,
        endDate: 0,
    }]);

    if (role === 'PERSONAL') {
        useEffect(() => {
            const request = async () => {
                const response = await apiGet(`${API_GATEWAY_URL}${HOLIDAY_URL}${FIND_ALL_BY_PERSONAL_ID_URL}?personalId=${userProfile.id}&companyId=${userProfile.companyId}`, token);
                if (response.status === 200) {
                    setHolidays(response.data);
                } else {

                }
            }
            request()

        }, []);
    }

    const handleSelectSlot = ({start, end}) => {
        setSelected([start, end]);
    };

    const dayPropGetter = (date) => {
        const day = date.getDay();
        const backgroundColor = day === 0 || day === 6 ? '#cccfd3' : '#e8f1f5';

        return {style: {backgroundColor}};
    };
    return (
        <div style={{width: '90%', margin: 'auto', height: '500px'}}>
            <Calendar
                selectable
                localizer={myLocalizer}
                style={{backgroundColor: 'white', color: ' black', height: '500'}}
                events={role === 'MANAGER' ? (personals.length !== 0 ? data.holidays.filter(holiday => personals.some(value => holiday.personals.includes(value))).map(holiday => ({
                    id: holiday.id,
                    what: 'holiday',
                    personals: holiday.personals.length,
                    title: holiday.name,
                    start: new Date(`${holiday.startDate}`),
                    end: new Date(`${holiday.endDate}`),
                    allDay: true,
                    desc: holiday.description
                })) : [...data.holidays.map(holiday => ({
                    id: holiday.id,
                    what: 'holiday',
                    personals: holiday.personals.length,
                    title: holiday.name,
                    start: new Date(`${holiday.startDate}`),
                    end: new Date(`${holiday.endDate}`),
                    allDay: true,
                    desc: holiday.description
                })), ...data.payments.map(payment => ({
                    id: payment.id,
                    what: 'payment',
                    title: payment.expenditureType,
                    start: new Date(`${payment.dueDate}`),
                    end: new Date(`${payment.dueDate}`),
                    allDay: true,
                    desc: payment.description
                }))]) : (holidays.map(holiday => ({
                    id: holiday.id,
                    what: 'holiday',
                    title: holiday.name,
                    start: new Date(`${holiday.startDate}`),
                    end: new Date(`${holiday.endDate}`),
                    allDay: true,
                    desc: holiday.description
                })))}
                onSelectSlot={handleSelectSlot}
                selected={selected}
                dayPropGetter={dayPropGetter}
                eventPropGetter={
                    (event, start, end, isSelected) => {
                        let newStyle = {
                            backgroundColor: "lightgrey",
                            color: 'black',
                            borderRadius: "0px",
                            border: "none"
                        };

                        if (event.what === "payment") {
                            newStyle.backgroundColor = data.payments.filter(payment => payment.expenditureType === event.title)[0].type === 'INCOME' ? "green" : "red";
                        } else if (event.what === "holiday") {
                            newStyle.backgroundColor = event.personals === 1 ? "yellow" : "purple";
                        }
                        return {
                            className: "",
                            style: newStyle
                        };
                    }
                }
            />
            <DateRangePicker personals={personals} setPersonals={setPersonals}/>
        </div>
    );
};

export default MyCalendar;
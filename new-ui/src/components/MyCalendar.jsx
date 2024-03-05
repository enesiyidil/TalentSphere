import React, {useContext, useEffect, useState} from 'react';
import {Calendar, momentLocalizer} from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';
import {useSelector} from "react-redux";
import {API_GATEWAY_URL, FIND_ALL_BY_PERSONAL_ID_URL, HOLIDAY_URL, SAVE_BY_PERSONAL} from "../constant/Endpoints.js";
import {ApiContext} from "../context/ApiContext.jsx";
import dayjs from 'dayjs';
import 'dayjs/locale/tr';
import styles from "../Css/ApproveComment.module.css";


const DateRangePicker = () => {
    const [startDate, setStartDate] = useState(dayjs());
    const [endDate, setEndDate] = useState(dayjs());
    const [description, setDescription] = useState('');
    const [name, setName] = useState('');
    const {apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);

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
        const response =await apiPost(`${API_GATEWAY_URL}${HOLIDAY_URL}${SAVE_BY_PERSONAL}`, {
            name: name,
            description: description,
            startDate: startDate,
            endDate: endDate,
            personalId: userProfile.id,
            companyId: userProfile.companyId
        }, token);
        if (response.status === 200) {
            handleClear();
        } else {
            alert('Connection Error')
        }
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <div  style={{marginTop:'10px'}} >
                    <label  style={{color:'white', fontWeight:' 550',padding:'0 10px'}} htmlFor="start-date" className="flex text-md font-medium text-gray-700">
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
                    <label  style={{color:'white', fontWeight:' 550',padding:'0 10px'}} htmlFor="end-date" className="block text-sm font-medium text-gray-700">
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
                    <label   style={{color:'white', fontWeight:' 550',padding:'0 10px'}} htmlFor="description" className="block text-sm font-medium text-gray-700">
                        Name
                    </label>
                    <input
                        style={{width:'15%'}}
                        type="text"
                        id="namae"
                        name="name"
                        value={name}
                        onChange={handleNameChange}
                        className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                </div>
                <div>
                    <label  style={{color:'white', fontWeight:' 550',padding:'0 10px'}} htmlFor="description" className="block text-sm font-medium text-gray-700">
                        Description
                    </label>
                    <input
                        style={{width:'15%'}}
                        type="text"
                        id="description"
                        name="description"
                        value={description}
                        onChange={handleDescriptionChange}
                        className="mt-1 p-2 block w-full border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                    />
                </div>
                <div className={styles["button-wrapper"]}>
                    <button onClick={handleSubmit} type="button" className={styles["button"]}
                    >
                        Submit
                    </button>
                    <button type='button' onClick={handleClear} className={styles["button"]}
                    >Clear
                    </button>
                </div>
            </form>
        </div>
    );
};


const myLocalizer = momentLocalizer(moment);

const MyCalendar = () => {

    const data = useSelector((state) => state.data);
    const role = useSelector((state) => state.role);
    const {apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const userProfile = useSelector((state) => state.userProfile);
    const [selected, setSelected] = useState([]); // selected state'ini tanımla
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
                const response =await apiGet(`${API_GATEWAY_URL}${HOLIDAY_URL}${FIND_ALL_BY_PERSONAL_ID_URL}?personalId=${userProfile.id}&companyId=${userProfile.companyId}`, token);
                if (response.status === 200) {
                    setHolidays(response.data);
                } else {

                }
            }
            request()

        }, []);
    }

    const handleSelectSlot = ({start, end}) => {
        setSelected([start, end]); // selected state'ini güncelle
    };

    const dayPropGetter = (date) => {
        const day = date.getDay();
        const backgroundColor = day === 0 || day === 6 ? '#cccfd3' : '#e8f1f5'; // Hafta sonu günlerini farklı renkte yapmak için

        return {style: {backgroundColor}};
    };

    // console.log(new Date(`${data.holidays[0].endDate}`))
    // console.log(new Date(`${data.holidays[0].endDate.substring(0,10)}`))
    // console.log(data.holidays[0].endDate)
    // console.log(new Date())
    console.log(holidays)
    return (
        <div style={{width:'90%',margin:'auto',height:'500px'}}>
            <Calendar
                selectable
                localizer={myLocalizer}
                style={{backgroundColor:'white',color:' black',height: '500'}}
                events={role === 'MANAGER' ? (data.holidays.map(holiday => ({
                    title: holiday.name,
                    start: new Date(`${holiday.startDate}`),
                    end: new Date(`${holiday.endDate}`),
                    allDay: true,
                    desc: holiday.description
                }))) : (holidays.map(holiday => ({
                    title: holiday.name,
                    start: new Date(`${holiday.startDate}`),
                    end: new Date(`${holiday.endDate}`),
                    allDay: true,
                    desc: holiday.description
                })))}
                onSelectSlot={handleSelectSlot}
                selected={selected}
                dayPropGetter={dayPropGetter}

            />
            {role === 'PERSONAL' && <DateRangePicker/>}
        </div>
    );
};

export default MyCalendar;
import React, {useContext, useEffect, useState} from "react";
import {useSelector} from "react-redux";
import '../Css/mainpage.css'
import AddCompany from "./AddCompany.jsx";
import {ManagerContext} from "../context/ManagerContext.jsx";
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { Calendar, momentLocalizer } from 'react-big-calendar';
import moment from 'moment';
import 'react-big-calendar/lib/css/react-big-calendar.css';



export default function ManagerPage() {
    const {
        handleSetData,
        handleAddPersonal,
        handleDeletePersonal,
        handleAddCompany,
        handleDeleteCompany,
        handleAddHoliday,
        handleDeleteHoliday,
        isLoading
    } = useContext(ManagerContext);
    const userProfile = useSelector((state) => state.userProfile);
    const data = useSelector((state) => state.data);
    const [company, setCompany] = useState();
    const [selectedDate, setSelectedDate] = useState(null);
    useEffect(() => {
        handleSetData();
    }, []);


    return (<>
        {isLoading ? (
            <div className="loader"></div>
        ) : (
        <div>
            {userProfile.companies.length ? (<div>
                <div>
                    <label htmlFor="role">Şirket adı : </label>
                    <select

                        className="textarea"
                        onChange={(event) => {
                            setCompany(data.companies.filter(item => item.id == event.target.value)[0]);

                        }}
                    >
                        <option key={0} value={0}>Seçiniz</option>
                        {data.companies.map((item, index) => (
                            <option key={index} value={item.id}>{item.name}</option>))}
                    </select>
                </div>
                <div>{company ? (<p>Manager id: {company.managerId}</p>) : (<p>Şirket seç</p>)}</div>
                <div style={{display: "flex", gap: "10px"}}>
                    <img src="../../public/company.jpg" alt=""/>
                    <div>
                        {(company && company.shifts.length) ? <div>
                            <p>{company.shifts[0].name}</p>
                            <p>{company.shifts[0].startTime}</p>
                            <p>{company.shifts[0].endTime}</p>
                        </div> : <div>

                        </div>}
                    </div>
                </div>
                {/*<div>*/}
                {/*    <h2>Choose a Date:</h2>*/}
                {/*    <DatePicker*/}
                {/*        selected={selectedDate}*/}
                {/*        onChange={date => setSelectedDate(date)}*/}
                {/*        dateFormat="yyyy-MM-dd"*/}
                {/*    />*/}
                {/*    {selectedDate && <p>You selected: {selectedDate.toLocaleDateString()}</p>}*/}
                {/*</div>*/}

            </div>) : (<AddCompany/>)}
        </div>)}
    </>)
}
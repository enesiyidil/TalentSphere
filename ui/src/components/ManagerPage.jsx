import React, {useContext, useEffect, useState} from "react";
import {useSelector} from "react-redux";
import '../Css/mainpage.css'
import AddCompany from "./AddCompany.jsx";
import {ManagerContext} from "../context/ManagerContext.jsx";

export default function ManagerPage() {
    const {
        handleSetData,
        handleAddPersonal,
        handleDeletePersonal,
        handleAddCompany,
        handleDeleteCompany,
        handleAddHoliday,
        handleDeleteHoliday,
    } = useContext(ManagerContext);
    const userProfile = useSelector((state) => state.userProfile);
    const data = useSelector((state) => state.data);
    const [company, setCompany] = useState()
    useEffect(() => {
        handleSetData();
    }, []);


    return (<>
        {!data.companies.length ? (
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
                <div>
                    <img src="../../public/company.jpg" alt=""/>
                </div>
                <div></div>
            </div>) : (<AddCompany/>)}
        </div>)}
    </>)
}
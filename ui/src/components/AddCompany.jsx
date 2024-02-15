import React, {useContext, useState} from "react";
import {useSelector} from "react-redux";
import '../Css/mainpage.css'
import {ManagerContext} from "../context/ManagerContext.jsx";

export default function AddCompany() {
    const {handleAddCompany} = useContext(ManagerContext)
    const userProfile = useSelector((state) => state.userProfile);
    const [name, setName] = useState("")
    return (<>
        <div>
            <label>Company Name</label>
            <input type="text" value={name}
                   onChange={(event) => setName(event.target.value)} />
            <button onClick={() => handleAddCompany({managerId: userProfile.id, name: name})}>Kaydet</button>
        </div>
    </>)
}
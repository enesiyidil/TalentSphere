import PropTypes from "prop-types";
import styles from "../Css/PersonalCard.module.css";
import * as React from "react";
import {useContext, useState} from "react";
import {ApiContext} from "../context/ApiContext";
import {useDispatch, useSelector} from "react-redux";
import {API_GATEWAY_URL, PERSONAL_URL, SAVE_URL, UPDATE_URL} from "../constant/Endpoints.js";
import {addPersonal, updatePersonal} from "../redux/actions.js"
import {MenuItem} from "@mui/material";
import Select from "@mui/material/Select";

const PersonalCard = ({personal, editing, setAddPersonal}) => {
    const [editPersonal, setEditPersonal] = useState(editing);
    const [name, setName] = useState(personal ? personal.name : "");
    const [surname, setSurname] = useState(personal ? personal.surname : "");
    const [email, setEmail] = useState(personal ? personal.email : "");
    const [phone, setPhone] = useState(personal ? personal.phone : "");
    const [title, setTitle] = useState(personal ? personal.title : "");
    const [photo, setPhoto] = useState(personal ? personal.photo : "");
    const [salary, setSalary] = useState(personal ? personal.salary : "");
    const [gender, setGender] = useState(personal ? personal.gender : "");

    const [isDeletedLoading, setIsDeletedLoading] = useState(false);
    const {apiPatch, apiDelete, apiPost} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const data = useSelector((state) => state.data);
    const dispatch = useDispatch()
    const [shiftId, setShiftId] = useState(personal ? personal.shiftId : "");


    const handleUpdateClick = (e) => {
        console.log(editPersonal);
        e.preventDefault();
        const request = async () => {
            const response = await apiPatch(`${API_GATEWAY_URL}${PERSONAL_URL}${UPDATE_URL}`, {
                id: personal.id,
                name,
                surname,
                email,
                phone,
                title,
                photo,
                salary,
                shiftId,
                companyId: data.id,
                managerId: data.managerId

            }, token);
            if (response.status === 200) {
                dispatch(updatePersonal(response.data))
                setEditPersonal(false);
            } else {

            }
        }
        request()

    };
    const handleSaveClick = (e) => {
        console.log(editPersonal);
        e.preventDefault();
        const request = async () => {
            const response = await apiPost(`${API_GATEWAY_URL}${PERSONAL_URL}${SAVE_URL}`, {
                name,
                surname,
                email,
                phone,
                title,
                photo,
                salary,
                shiftId,
                companyId: data.id,
                managerId: data.managerId,
                gender
            }, token);
            if (response.status === 200) {
                dispatch(addPersonal(response.data))
                setEditPersonal(false);
                setAddPersonal(false)
            } else {

            }
        }
        request()

    };


    return (
        <>
            {editPersonal ? (
                <div className={styles["personal-card"]}>
                    <form className={styles["update-personal-form"]}>
                        <p>Image URL</p>
                        <input
                            onChange={(e) => setPhoto(e.target.value)}
                            value={photo}
                            type="text"
                            className={"textarea"}
                        />
                        <p>Title</p>
                        <input
                            onChange={(e) => setTitle(e.target.value)}
                            value={title}
                            type="text"
                            className={"textarea"}
                        />
                        <p>Name</p>
                        <input
                            onChange={(e) => setName(e.target.value)}
                            value={name}
                            type="text"
                            className={"textarea"}
                        />
                        <p>Surname</p>
                        <input
                            onChange={(e) => setSurname(e.target.value)}
                            value={surname}
                            type="text"
                            className={"textarea"}
                        />
                        <p>Phone</p>
                        <input
                            onChange={(e) => setPhone(e.target.value)}
                            value={phone}
                            type="text"
                            className={"textarea"}
                        />
                        <p>Email</p>
                        <input
                            onChange={(e) => setEmail(e.target.value)}
                            value={email}
                            type="text"
                            className={"textarea"}
                        />
                        <p>Salary</p>
                        <input
                            onChange={(e) => setSalary(e.target.value)}
                            value={salary}
                            type="text"
                            className={"textarea"}
                        />
                        <Select
                            id="select"
                            value={shiftId}
                            label="Shift"
                            className={"textarea"}
                            onChange={e => setShiftId(e.target.value)}
                        >
                            {data.shifts.map(shift => (
                                <MenuItem key={shift.id} value={shift.id}>{shift.name}</MenuItem>
                            ))}
                        </Select>
                        <Select
                            id="gender"
                            label="Gender"
                            value={gender}
                            onChange={e => setGender(e.target.value)}
                        >
                            <MenuItem value="MAN">MAN</MenuItem>
                            <MenuItem value="WOMAN">WOMAN</MenuItem>
                            <MenuItem value="NO_GENDER">NO_GENDER</MenuItem>
                        </Select>

                        <div className={styles["button"]}>
                            <button
                                className={styles["button"]}
                                onClick={(e) => {
                                    if (editPersonal) {
                                        if (editing) {
                                            handleSaveClick(e)
                                        } else {
                                            handleUpdateClick(e)
                                        }
                                    } else {
                                        setEditPersonal(true)
                                    }
                                }}
                            >
                                {editPersonal ? "Save" : "Update"}
                            </button>
                        </div>

                        <div className={styles["button"]}>
                            <button
                                className={styles["button"]}
                                onClick={(e) => {
                                    if (editPersonal) {
                                        setEditPersonal(false)
                                    } else {
                                        //handleDeleteClick(e)
                                    }
                                }}
                            >
                                {editPersonal ? "Cancel" : "Delete"}
                            </button>

                        </div>

                    </form>
                </div>
            ) : (

                <div className={styles["personal-card"]}>
                    <img src={personal.photo} alt=""/>
                    <h3>{personal.title}</h3>
                    <p>{personal.name}</p>
                    <p>{personal.surname}</p>
                    <p>{personal.phone}</p>
                    <p>{personal.email}</p>
                    <p>{personal.salary}</p>
                    <div className={styles["button"]}>
                        <button
                            className={styles["button"]}
                            onClick={(e) => {
                                if (editPersonal) {
                                    if (editing) {
                                        handleSaveClick(e)
                                    } else {
                                        handleUpdateClick(e)
                                    }
                                } else {
                                    setEditPersonal(true)
                                }
                            }}
                        >
                            {editPersonal ? "Save" : "Update"}
                        </button>
                    </div>

                    <div className={styles["button"]}>
                        <button
                            className={styles["button"]}
                            onClick={(e) => {
                                if (editPersonal) {
                                    setEditPersonal(false)
                                } else {
                                    //handleDeleteClick(e)
                                }
                            }}
                        >
                            {editPersonal ? "Cancel" : "Delete"}
                        </button>
                    </div>
                </div>
            )}
        </>
    );

};

//burası hata görüntüü olmasın diye gerekli değil
PersonalCard.propTypes = {
    personal: PropTypes.object
};

export default PersonalCard;
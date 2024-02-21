import Badge from '@mui/material/Badge';
import styles from "../Css/AdminHome.module.css";
import {useContext, useEffect, useState} from "react";
import {ApiContext} from "../context/ApiContext.jsx";
import {useSelector} from "react-redux";
import {ADMIN_URL, API_GATEWAY_URL, GET_INFORMATION} from "../constant/Endpoints.js";

export function AdminHome() {

    const {apiGet} = useContext(ApiContext);
    const token = useSelector((state) => state.token);
    const [information, setInformation] = useState({
        managerNumber: 0,
        personalNumber: 0,
        visitorNumber: 0,
        companyNumber: 0,
        paymentNumber: 0,
        commentNumber: 0
    });

    useEffect(() => {
        const response = apiGet(`${API_GATEWAY_URL}${ADMIN_URL}${GET_INFORMATION}`, token);
        if (response.status === 200) {
            setInformation(response.data);
        } else {

        }
    }, []);

    return (
        <>
            <div className={styles["img-wrapper"]}>
                <Badge color="error" badgeContent={information.personalNumber}>
                    <div className={styles["img-div"]}>
                        <img src={"/personal.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.managerNumber}>
                    <div className={styles["img-div"]}>
                        <img src={"/manager.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.visitorNumber}>
                    <div className={styles["img-div"]}>
                        <img src={"/visitor.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.companyNumber}>
                    <div className={styles["img-div"]}>
                        <img src={"/company.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.paymentNumber}>
                    <div className={styles["img-div"]}>
                        <img src={"/payment.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.commentNumber}>
                    <div className={styles["img-div"]}>
                        <img src={"/comment.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
            </div>
        </>
    )
}
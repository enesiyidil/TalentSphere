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
        managerSize: 0,
        personalSize: 0,
        visitorSize: 0,
        companySize: 0,
        paymentSize: 0,
        commentSize: 0
    });

    useEffect( () => {
        const request = async () => {
            const response = await apiGet(`${API_GATEWAY_URL}${ADMIN_URL}${GET_INFORMATION}`, token);
            if (response.status === 200) {
                setInformation(response.data);
            } else {

            }
        }

        request()

    }, []);

    return (
        <>
            <div className={styles["img-wrapper"]}>
                <Badge color="error" badgeContent={information.personalSize}>
                    <div className={styles["img-div"]}>
                        <img src={"/personal.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.managerSize}>
                    <div className={styles["img-div"]}>
                        <img src={"/manager.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.visitorSize}>
                    <div className={styles["img-div"]}>
                        <img src={"/visitor.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.companySize}>
                    <div className={styles["img-div"]}>
                        <img src={"/company.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.paymentSize}>
                    <div className={styles["img-div"]}>
                        <img src={"/payment.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
                <Badge color="error" badgeContent={information.commentSize}>
                    <div className={styles["img-div"]}>
                        <img src={"/comment.png"} alt="" className={styles["img"]}/>
                    </div>
                </Badge>
            </div>
        </>
    )
}
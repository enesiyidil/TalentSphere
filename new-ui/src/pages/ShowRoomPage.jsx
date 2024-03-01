import styles from "../Css/ShowRoomPage.module.css";
import Clock from "../components/Clock.jsx";



const ShowRoomPage = () => {

    return (
        <>
            <main className={styles["show-room-wrapper"]}>
                <div className={styles["container"]}>
                    <div className={styles["circle1"]}></div>
                    <div className={styles["circle2"]}></div>
                </div>
                <div className={styles["clock-container"]}>
                    <Clock/>
                </div>
            </main>
        </>
    )
}

export default ShowRoomPage;
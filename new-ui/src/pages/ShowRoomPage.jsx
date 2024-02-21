import styles from "../Css/ShowRoomPage.module.css";

const ShowRoomPage = () => {

    return (
        <>
            <main className={styles["show-room-wrapper"]}>
                <div className={styles["container"]}>
                    <div className={styles["circle1"]}></div>
                    <div className={styles["circle2"]}></div>
                </div>
            </main>
        </>
    )
}

export default ShowRoomPage;
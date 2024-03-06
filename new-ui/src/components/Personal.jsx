import styles from "../Css/Personal.module.css";
import { useSelector } from "react-redux";
import PersonalCard from "./PersonalCard.jsx";
import React, { useState } from "react";

export default function Personal() {
    const data = useSelector((state) => state.data);
    const [addPersonal, setAddPersonal] = useState(false);

    const handleAddPersonalClick = () => {
        setAddPersonal(true);
    };

    return (
        <>



            {data.personals && data.personals.map(personal =>
                <PersonalCard key={personal.id} personal={personal} editing={false} />)}
            {addPersonal ? (
                <PersonalCard key="" personal={{}} editing={true} setAddPersonal={setAddPersonal} />
            ) : (
                <div className={styles["button"]}>
                    <button onClick={handleAddPersonalClick} type="button" className={styles["button"]}>
                        Add Personal
                    </button>
                </div>
            )}

        </>
    )
}

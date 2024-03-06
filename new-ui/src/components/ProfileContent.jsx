import styles from "../Css/ProfileContent.module.css";
import PropTypes from "prop-types";
import {useSelector} from "react-redux";

const ProfileContent = ({editing, editedUserProfile, setEditedUserProfile}) => {

    const userProfile = useSelector((state) => state.userProfile);
    const role = useSelector((state) => state.role);

    return (
        <>
            {/*<div className={styles["user-profile-container"]}>*/}
            <div className={styles["profile-img-wrapper"]}>
                {userProfile.photo ? <img src={userProfile.photo} alt=""/> : <img src="personal.png" alt=""/>}
            </div>
            <div className={styles["profile-wrapper"]}>
                <div className={styles["content-wrapper"]}>
                    <span className={styles["label2"]}>Name: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.name}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, name: e.target.value}))}

                        />)
                        :
                        (<span className={styles["text"]}>{userProfile.name}</span>)}
                </div>

                <div className={styles["content-wrapper"]}>
                    <span className={styles["label2"]}>Surname: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.surname}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, surname: e.target.value}))}
                        />)
                        :
                        (<span className={styles["text"]}>{userProfile.surname}</span>)}
                </div>

                <div className={styles["content-wrapper"]}>
                    <span className={styles["label2"]}>E-Mail: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.email}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, email: e.target.value}))}
                        />)
                        :
                        (<span className={styles["text"]}>{userProfile.email}</span>)}
                </div>

                <div className={styles["content-wrapper"]}>
                    <span className={styles["label2"]}>Phone: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.phone}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, phone: e.target.value}))}
                        />)
                        :
                        (<span className={styles["text"]}>{userProfile.phone}</span>)}
                </div>


                {(role === 'MANAGER' || role === 'PERSONAL') && (
                    <div className={styles["content-wrapper"]}>
                        <span className={styles["label2"]}>Title: </span>
                        {editing ?
                            (<input
                                value={editedUserProfile.title}
                                onChange={e => setEditedUserProfile(prevState => ({
                                    ...prevState,
                                    title: e.target.value
                                }))}
                            />)
                            :
                            (<span className={styles["text"]}>{userProfile.title}</span>)}
                    </div>
                )}

                {role === 'MANAGER' && (
                    <>
                        <div className={styles["content-wrapper"]}>
                            <span className={styles["label2"]}>Personals Number: </span>
                            <span
                                className={styles["text"]}>{userProfile.personals && userProfile.personals.length}</span>
                        </div>
                        <div className={styles["content-wrapper"]}>
                            <span className={styles["label2"]}>Packet: </span>
                            <span
                                className={styles["text"]}>{userProfile.packet}</span>
                        </div>
                    </>
                )}

                {role === 'PERSONAL' && (
                    <div className={styles["content-wrapper"]}>
                        <span className={styles["label2"]}>Salary: </span>
                        <span className={styles["text"]}>{userProfile.salary}</span>
                    </div>
                )}

                <div className={styles["content-wrapper"]}>
                    <span className={styles["label2"]}>Created Date: </span>
                    <span className={styles["text"]}>{userProfile.createdDateTime}</span>
                </div>

                <div className={styles["content-wrapper"]}>
                    <span className={styles["label2"]}>Updated Date: </span>
                    <span className={styles["text"]}>{userProfile.updatedDateTime}</span>
                </div>
            </div>
            {/*</div>*/}
        </>
    )
}

ProfileContent.propTypes = {
    editing: PropTypes.bool,
    editedUserProfile: PropTypes.object,
    setEditedUserProfile: PropTypes.func,
};

export default ProfileContent;
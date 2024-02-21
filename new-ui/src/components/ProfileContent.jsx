import styles from "../Css/ProfileContent.module.css";
import PropTypes from "prop-types";
import {useSelector} from "react-redux";

const ProfileContent = ({editing, editedUserProfile, setEditedUserProfile}) => {

    const userProfile = useSelector((state) => state.userProfile);
    const role = useSelector((state) => state.role);

    return (
        <>
            <div className={styles["profile-img-wrapper"]}>
                <img src={userProfile.photo} alt="" />
            </div>
            <div className={styles["profile-wrapper"]}>
                <div className={styles["content-wrapper"]}>
                    <span>Name: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.name}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, name: e.target.value}))}
                        />)
                        :
                        (<span>{userProfile.name}</span>)}
                </div>

                <div className={styles["content-wrapper"]}>
                    <span>Surname: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.surname}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, surname: e.target.value}))}
                        />)
                        :
                        (<span>{userProfile.surname}</span>)}
                </div>

                <div className={styles["content-wrapper"]}>
                    <span>E-Mail: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.email}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, email: e.target.value}))}
                        />)
                        :
                        (<span>{userProfile.email}</span>)}
                </div>

                <div className={styles["content-wrapper"]}>
                    <span>Phone: </span>
                    {editing ?
                        (<input
                            value={editedUserProfile.phone}
                            onChange={e => setEditedUserProfile(prevState => ({...prevState, phone: e.target.value}))}
                        />)
                        :
                        (<span>{userProfile.phone}</span>)}
                </div>

                {(role === 'MANAGER' || role === 'PERSONAL') && (
                        <div className={styles["content-wrapper"]}>
                            <span>Title: </span>
                            {editing ?
                                (<input
                                    value={editedUserProfile.title}
                                    onChange={e => setEditedUserProfile(prevState => ({
                                        ...prevState,
                                        title: e.target.value
                                    }))}
                                />)
                                :
                                (<span>{userProfile.email}</span>)}
                        </div>
                )}

                {role === 'MANAGER' && (
                        <div className={styles["content-wrapper"]}>
                            <span>Personals Number: </span>
                            <span>{userProfile.personals.length}</span>
                        </div>
                )}

                {role === 'PERSONAL' && (
                    <div className={styles["content-wrapper"]}>
                        <span>Salary: </span>
                        <span>{userProfile.salary}</span>
                    </div>
                )}

                <div className={styles["content-wrapper"]}>
                    <span>Created Date: </span>
                    <span>{userProfile.createdDate}</span>
                </div>

                <div className={styles["content-wrapper"]}>
                    <span>Updated Date: </span>
                    <span>{userProfile.updatedDate}</span>
                </div>
            </div>
        </>
    )
}

ProfileContent.propTypes = {
    editing: PropTypes.bool,
    editedUserProfile: PropTypes.object,
    setEditedUserProfile: PropTypes.func,
};

export default ProfileContent;
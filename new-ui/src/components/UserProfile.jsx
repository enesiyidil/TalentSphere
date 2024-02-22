import {useDispatch, useSelector} from "react-redux";
import {useContext, useEffect, useState} from "react";
import {UserProfileContext} from "../context/UserProfileContext.jsx";
import EditIcon from '@mui/icons-material/Edit';
import EditOffIcon from '@mui/icons-material/EditOff';
import PersonRemoveIcon from '@mui/icons-material/PersonRemove';
import styles from "../Css/UserProfile.module.css";
import {confirmAlert} from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import PersonIcon from '@mui/icons-material/Person';
import ProfileContent from "./ProfileContent.jsx";
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import {setRole, setSwitchAccount} from "../redux/actions.js";

export const UserProfile = () => {

    const userProfile = useSelector((state) => state.userProfile);
    const role = useSelector((state) => state.role);
    const switchAccount = useSelector((state) => state.switchAccount);
    const dispatch = useDispatch();
    const {handleSetUserProfile, handleUpdateUserProfile, handleDeleteUserProfile, isLoading} = useContext(UserProfileContext);
    const [editing, setEditing] = useState(false);
    const [editedUserProfile, setEditedUserProfile] = useState(userProfile);

    useEffect(() => {
        handleSetUserProfile();
    }, []);

    const handleEditIcon = () => {
        setEditing(true);
    }

    const handleEditOffIcon = () => {
        setEditing(false);
        setEditedUserProfile(userProfile);
    }

    const handlePersonRemoveIcon = () => {
        confirmAlert({
            title: 'Confirm to delete',
            message: 'Your account will be permanently deleted. Do you confirm?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => handleDeleteUserProfile
                },
                {
                    label: 'No',
                }
            ]
        });
    }

    const handleManageAccountsIcon = () => {
        dispatch(setSwitchAccount(false));
        dispatch(setRole('MANAGER'));
        handleSetUserProfile();
    }

    const handlePersonIcon = () => {
        dispatch(setSwitchAccount(true));
        dispatch(setRole('PERSONAL'));
        handleSetUserProfile();
    }

    const handleClearClick = () => {
        setEditedUserProfile(userProfile);
    }

    const handleSaveClick = () => {
        handleUpdateUserProfile(editedUserProfile);
        setEditing(false);
    }

    return (<>
        {isLoading ?
            (<div className="loader"></div>) :
            (<div>
                <div className={styles["button-wrapper"]}>
                    {(role === 'MANAGER' || switchAccount) && <>{switchAccount ?
                        <ManageAccountsIcon onClick={handleManageAccountsIcon}/> :
                        <PersonIcon onClick={handlePersonIcon}/>}</>}
                    {editing ? <EditOffIcon onClick={handleEditOffIcon}/> : <EditIcon onClick={handleEditIcon}/>}
                    <PersonRemoveIcon onClick={handlePersonRemoveIcon}/>
                </div>

                <div className={styles["content-wrapper"]}>
                    <ProfileContent editing={editing} editedUserProfile={editedUserProfile}
                                    setEditedUserProfile={setEditedUserProfile}/>
                </div>

                {editing && <>
                    <div className={styles["button-wrapper"]}>
                        <button onClick={handleSaveClick} type="button" className={styles["button"]}>
                            Save
                        </button>
                        <button type='button' onClick={handleClearClick} className={styles["button"]}>Clear</button>
                    </div>
                </>}
            </div>)}

    </>);
};
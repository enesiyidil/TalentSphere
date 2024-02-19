import {useSelector} from "react-redux";
import {useContext, useEffect} from "react";
import {UserProfileContext} from "../context/UserProfileContext.jsx";

export const UserProfile = () => {
    const userProfile = useSelector((state) => state.userProfile);
    const {handleSetUserProfile, handleUpdateUserProfile, isLoading} = useContext(UserProfileContext);

    useEffect(() => {
        handleSetUserProfile();
    }, []);

    return (<>
        <div>
            <div className="user-profile-img">
                <img src={userProfile.photo} alt={userProfile.photo}/>
            </div>
            <div>
                {Object.entries(userProfile).map(([key, value]) => (
                    <p key={key}>{key}: {value}</p>
                ))}
            </div>
        </div>
    </>);
};
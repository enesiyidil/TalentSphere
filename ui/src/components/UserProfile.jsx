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
            User Profile: {}
        </div>
    </>);
};
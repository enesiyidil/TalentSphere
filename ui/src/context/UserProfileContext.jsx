import {createContext, useContext, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {ApiContext} from "./ApiContext.jsx";
import {setUserProfile, updateUserProfile} from "../redux/actions.js"
import PropTypes from "prop-types";
import {
    ADMIN_URL,
    API_GATEWAY_URL,
    FIND_BY_AUTH_ID_URL,
    MANAGER_URL,
    PERSONAL_URL, UPDATE_URL,
    VISITOR_URL
} from "../constant/Endpoints.js";

export const UserProfileContext = createContext();

export const UserProfileContextProvider = ({children}) => {
    const role = useSelector((state) => state.role);
    const token = useSelector((state) => state.token);
    const authId = useSelector((state) => state.authId);
    const dispatch = useDispatch();
    const {apiGet, apiPatch} = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);

    let url;
    switch (role) {
        case 'ADMIN':
            url = API_GATEWAY_URL + ADMIN_URL;
            break;
        case 'MANAGER':
            url = API_GATEWAY_URL + MANAGER_URL;
            break;
        case 'PERSONAL':
            url = API_GATEWAY_URL + PERSONAL_URL;
            break;
        case 'VISITOR':
            url = API_GATEWAY_URL + VISITOR_URL;
            break;
    }

    async function handleSetUserProfile() {
        setIsLoading(true);
        console.log(`${url}${FIND_BY_AUTH_ID_URL}?authId=${authId}`)
        const responseData = await apiGet(`${url}${FIND_BY_AUTH_ID_URL}?authId=${authId}`);
        if (responseData.status === 200) {
            dispatch(setUserProfile(responseData.data))
        } else {
            // todo: navigate homepage and error message
        }
        setIsLoading(false);
    }

    async function handleUpdateUserProfile(user) {
        setIsLoading(true);
        const responseData = await apiPatch(`${url}${UPDATE_URL}`, user, token);
        if (responseData.status === 200) {
            dispatch(updateUserProfile(responseData.data))
        } else {
            // todo: error message
        }
        setIsLoading(false);
    }

    return (
        <UserProfileContext.Provider
            value={{
                handleSetUserProfile,
                handleUpdateUserProfile,
                isLoading,
            }}
        >
            {children}
        </UserProfileContext.Provider>
    );
};

UserProfileContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
import {createContext, useContext, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {ApiContext} from "./ApiContext.jsx";
import {setUserProfile, updateUserProfile} from "../redux/actions.js"
import PropTypes from "prop-types";

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
            url = process.env.REACT_APP_API_GATEWAY_URL + process.env.REACT_APP_ADMIN_URL;
            break;
        case 'MANAGER':
            url = process.env.REACT_APP_API_GATEWAY_URL + process.env.REACT_APP_MANAGER_URL;
            break;
        case 'PERSONAL':
            url = process.env.REACT_APP_API_GATEWAY_URL + process.env.REACT_APP_PERSONAL_URL;
            break;
        case 'VISITOR':
            url = process.env.REACT_APP_API_GATEWAY_URL + process.env.REACT_APP_VISITOR_URL;
            break;
    }

    async function handleSetUserProfile() {
        setIsLoading(true);
        const responseData = await apiGet(`${url}${process.env.REACT_APP_FIND_BY_AUTH_ID_URL}?authId=${authId}`, token);// todo: get isteğini kotrol et
        console.log(responseData)
        // todo: data başarılı mı değil mi kontrol et ona göre setleme yap
        dispatch(setUserProfile(responseData.data))
        setIsLoading(false);
    }

    async function handleUpdateUserProfile(user) {
        setIsLoading(true);
        const responseData = await apiPatch(`${url}${process.env.REACT_APP_FIND_BY_AUTH_ID_URL}`, user, token);// todo: get isteğini kotrol et
        console.log(responseData)
        // todo: data başarılı mı değil mi kontrol et ona göre setleme yap
        dispatch(updateUserProfile(responseData.data))
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
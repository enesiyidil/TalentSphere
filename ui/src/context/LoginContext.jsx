import {createContext, useContext, useState} from "react";
import {ApiContext} from "./ApiContext";
import {useDispatch} from "react-redux";
import PropTypes from "prop-types";
import {setRole, setToken, setAuthId} from "../redux/actions.js"

export const LoginContext = createContext();

export const LoginContextProvider = ({children}) => {
    const {apiPost} = useContext(ApiContext);
    const dispatch = useDispatch();
    const [isLoading, setIsLoading] = useState(false);

    async function doLogin(email, password) {
        setIsLoading(true);
        const responseData = await apiPost(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_AUTH_URL}${process.env.REACT_APP_LOGIN_URL}`, {email, password});
        console.log(responseData);
        // todo: login başarılı mı değil mi kontrol et ona göre setleme yap
        dispatch(setRole(responseData.data.role));
        dispatch(setToken(responseData.data.token));
        dispatch(setAuthId(responseData.data.authId));
        setIsLoading(false);
    }

    return (
        <LoginContextProvider.Provider
            value={{
                doLogin,
                isLoading,
            }}
        >
            {children}
        </LoginContextProvider.Provider>
    );
};

LoginContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
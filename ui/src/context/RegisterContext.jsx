import {createContext, useContext, useState} from "react";
import {ApiContext} from "./ApiContext";
import PropTypes from "prop-types";

export const RegisterContext = createContext();

export const RegisterContextProvider = ({children}) => {
    const {apiPost} = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);

    async function doRegister(payload) {
        setIsLoading(true);
        const responseData = await apiPost(`${process.env.REACT_APP_API_GATEWAY_URL}${process.env.REACT_APP_AUTH_URL}${process.env.REACT_APP_LOGIN_URL}`, payload);
        console.log(responseData)
        // todo: kayıt başarılımı değil mi kontrol edip çıktı ver
        setIsLoading(false);
    }

    return (
        <RegisterContextProvider.Provider
            value={{
                doRegister,
                isLoading,
            }}
        >
            {children}
        </RegisterContextProvider.Provider>
    );
};

RegisterContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
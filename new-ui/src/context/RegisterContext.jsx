import {createContext, useContext, useState} from "react";
import {ApiContext} from "./ApiContext";
import PropTypes from "prop-types";
import {API_GATEWAY_URL, AUTH_URL, REGISTER_URL} from "../constant/Endpoints.js";

export const RegisterContext = createContext();

export const RegisterContextProvider = ({children}) => {
    const {apiPost} = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);

    async function handleRegister(payload) {
        setIsLoading(true);
        const responseData = await apiPost(`${API_GATEWAY_URL}${AUTH_URL}${REGISTER_URL}`, {
            ...payload,
            role: 'VISITOR'
        });
        if (responseData.status === 200) {
            // todo: navigate homepage and success message
        } else {
            // todo: navigate homepage and error message
        }
        setIsLoading(false);
    }

    return (
        <RegisterContext.Provider
            value={{
                handleRegister,
                isLoading,
            }}
        >
            {children}
        </RegisterContext.Provider>
    );
};

RegisterContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
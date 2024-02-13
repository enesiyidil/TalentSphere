import {createContext, useContext, useState} from "react";
import {ApiContext} from "./ApiContext";
import PropTypes from "prop-types";

export const RegisterContext = createContext();

export const RegisterContextProvider = ({children}) => {
    const {apiPost} = useContext(ApiContext);
    const [isLoading, setIsLoading] = useState(false);

    async function doRegister(payload) {
        setIsLoading(true);
        const responseData = await apiPost(`http://localhost:9092/auth/register`, payload);
        console.log(responseData)
        // todo: kayıt başarılımı değil mi kontrol edip çıktı ver
        setIsLoading(false);
    }

    return (
        <RegisterContext.Provider
            value={{
                doRegister,
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
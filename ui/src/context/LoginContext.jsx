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
        const responseData = await apiPost(`http://localhost:9092/auth/login`, {email, password});
        console.log(responseData);
        // todo: login başarılı mı değil mi kontrol et ona göre setleme yap
        dispatch(setRole(responseData.data.role));
        dispatch(setToken(responseData.data.token));
        dispatch(setAuthId(responseData.data.authId));
        setIsLoading(false);
    }

    return (
        <LoginContext.Provider
            value={{
                doLogin,
                isLoading,
            }}
        >
            {children}
        </LoginContext.Provider>
    );
};

// LoginContextProvider.propTypes = {
//     children: PropTypes..isRequired,
// };
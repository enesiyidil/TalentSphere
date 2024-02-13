import {createContext} from "react";
import axios from "axios";
import PropTypes from "prop-types";

// axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

export const ApiContext = createContext();

export const ApiContextProvider = ({children}) => {

    const apiGet = async (url, token) => {
        const response = await axios.get(url, token !== null ? {headers: {Authorization: "Bearer " + token}} : null);
        console.log("apiGet response => ", response);
        return response;
    };
    const apiPost = async (url, body, token) => {
        const response = await axios.post(url, body, token !== null ? {headers: {Authorization: "Bearer " + token}} : null);
        console.log("apiPost response => ", response);
        return response;
    };
    const apiPatch = async (url, body, token) => {
        const response = await axios.patch(url, body, token !== null ? {headers: {Authorization: "Bearer " + token}} : null);
        console.log("apiPatch response => ", response);
        return response;
    };
    const apiDelete = async (url, token) => {
        const response = await axios.delete(url, token !== null ? {headers: {Authorization: "Bearer " + token}} : null);
        console.log("apiDelete response => ", response);
        return response;
    };
    return (
        <ApiContext.Provider value={{apiGet, apiPost, apiPatch, apiDelete}}>
            {children}
        </ApiContext.Provider>
    );
};

ApiContextProvider.propTypes = {
    children: PropTypes.node.isRequired,
};

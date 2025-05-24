import React from "react";
import { useSearchParams } from "react-router-dom";
import FindId from "./FindId";
import FindPwVerify from "./FindPwVerify";
import FindPw from "./FindPw";

const ForgotIdOrPassword = () => {
    const [searchParams] = useSearchParams();
    const page = searchParams.get("page");

    return (
        <>
            {page === "id" && <FindId />}
            {page === "pw" && <FindPwVerify />}
            {page === "reset" && <FindPw />}
        </>
    );
};

export default ForgotIdOrPassword;

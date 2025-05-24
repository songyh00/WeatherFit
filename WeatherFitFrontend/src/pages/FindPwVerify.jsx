import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
    LoginWrapper,
    LoginSection,
    LoginInput,
    LoginButton,
    LoginTexts,
    JoinTheMembershipLink,
} from "../layout/login.style.js";
import logo from "../assets/logo.png";
import { getSeason, theme } from "../components/theme.js";

const FindPwVerify = () => {
    const navigate = useNavigate();
    const [season, setSeason] = useState(getSeason());
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);

    const handleVerify = async (e) => {
        e.preventDefault();

        if (!username.trim() || !email.trim()) {
            alert("아이디와 이메일을 모두 입력해주세요.");
            return;
        }

        setLoading(true);
        try {
            const response = await fetch("/api/auth/verify-reset-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, email }),
            });

            if (!response.ok) throw new Error("검증 실패");

            navigate("/ForgotIdOrPassword?page=reset", { state: { username } });
        } catch {
            alert("사용자 정보가 일치하지 않습니다. 다시 확인해주세요.");
        } finally {
            setLoading(false);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter") handleVerify(e);
    };

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);

    return (
        <LoginWrapper>
            <Link to="/"><img src={logo} alt="WeatherFit Logo" /></Link>
            <LoginSection>
                <LoginInput
                    style={{ marginTop: "20px" }}
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="아이디를 입력해주세요"
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                />
                <LoginInput
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="이메일을 입력해주세요"
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                />
                <LoginButton
                    type="button"
                    onClick={handleVerify}
                    disabled={loading}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $textColor={theme[season].textColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    {loading ? "확인 중..." : "본인 인증"}
                </LoginButton>
                <LoginTexts>
                    <JoinTheMembershipLink to="/Login">로그인</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="/ForgotIdOrPassword?page=id">| 아이디 찾기</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="/JoinTheMembership">| 회원가입</JoinTheMembershipLink>
                </LoginTexts>
            </LoginSection>
        </LoginWrapper>
    );
};

export default FindPwVerify;

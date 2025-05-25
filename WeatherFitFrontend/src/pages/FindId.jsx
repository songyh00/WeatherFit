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

const FindId = () => {
    const navigate = useNavigate();
    const [season, setSeason] = useState(getSeason());
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);

    const handleFindId = async (e) => {
        e.preventDefault();

        if (!email.trim() || !password.trim()) {
            alert("이메일과 비밀번호를 모두 입력해주세요.");
            return;
        }

        setLoading(true);
        try {
            const response = await fetch("/api/auth/find-username", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email, password }),
            });

            if (!response.ok) throw new Error("조회 실패");

            const data = await response.json();
            const username = data.username;

            if (username) {
                alert(`🔍 아이디: ${username}`);
                navigate("/Login");
            } else {
                alert("아이디를 찾을 수 없습니다. 이메일 또는 비밀번호를 확인해주세요.");
            }
        } catch {
            alert("아이디를 찾을 수 없습니다. 이메일 또는 비밀번호를 확인해주세요.");
        } finally {
            setLoading(false);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter") handleFindId(e);
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
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="이메일을 입력해주세요"
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                />
                <LoginInput
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="비밀번호를 입력해주세요"
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                />
                <LoginButton
                    type="button"
                    onClick={handleFindId}
                    disabled={loading}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $textColor={theme[season].textColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    {loading ? "조회 중..." : "아이디 찾기"}
                </LoginButton>
                <LoginTexts>
                    <JoinTheMembershipLink to="/Login">로그인</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="/ForgotIdOrPassword?page=pw">| 비밀번호 찾기</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="/JoinTheMembership">| 회원가입</JoinTheMembershipLink>
                </LoginTexts>
            </LoginSection>
        </LoginWrapper>
    );
};

export default FindId;

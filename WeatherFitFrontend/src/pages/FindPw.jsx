import React, { useState, useEffect } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";
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

const FindPw = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const username = location.state?.username;

    const [season, setSeason] = useState(getSeason());
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [loading, setLoading] = useState(false);

    const handleReset = async (e) => {
        e.preventDefault();

        if (!username || !newPassword || !confirmPassword) {
            alert("모든 정보를 입력해주세요.");
            return;
        }

        if (newPassword !== confirmPassword) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }

        setLoading(true);
        try {
            const response = await fetch("/api/auth/reset-password", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ username, newPassword, newPasswordConfirm: confirmPassword }),
            });

            if (!response.ok) throw new Error("재설정 실패");

            alert("✅ 비밀번호가 성공적으로 변경되었습니다.");
            navigate("/Login");
        } catch {
            alert("비밀번호 재설정에 실패했습니다. 다시 시도해주세요.");
        } finally {
            setLoading(false);
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter") handleReset(e);
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
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="새 비밀번호"
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                />
                <LoginInput
                    type="password"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    onKeyDown={handleKeyDown}
                    placeholder="새 비밀번호 확인"
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                />
                <LoginButton
                    type="button"
                    onClick={handleReset}
                    disabled={loading}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $textColor={theme[season].textColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    {loading ? "변경 중..." : "비밀번호 재설정"}
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

export default FindPw;

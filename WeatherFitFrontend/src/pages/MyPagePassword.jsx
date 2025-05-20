import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import { LoginWrapper, LoginSection, LoginInput, LoginButton } from "../layout/login.style.js";
import { Link, useNavigate } from "react-router-dom";   // ✅ useNavigate 추가
import logo from "../assets/logo.png";
import { getSeason, theme } from "../components/theme.js";
import {MyPageText, RegisterWrapper } from "../layout/Mypage_info.style.js";

const MyPagePassword = () => {
    const [season, setSeason] = useState(getSeason());
    const [inputFocused, setInputFocused] = useState(false);
    const [password, setPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [newPasswordConfirm, setNewPasswordConfirm] = useState("");


    const passwordRef = useRef(null);
    const newPasswordRef = useRef(null);
    const newPasswordConfirmRef = useRef(null);
    const navigate = useNavigate(); // ✅ 페이지 이동용

    const handleFocus = () => setInputFocused(true);
    const handleBlur = () => setInputFocused(false);

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);




    const handleSubmit = async (e) => {
        e.preventDefault();

        if (password.trim() === "") {
            alert("기존 비밀번호를 입력해주세요.");
            passwordRef.current?.focus();
            return;
        } else if (newPassword.trim() === "") {
            alert("새 비밀번호를 입력해주세요.");
            newPasswordRef.current?.focus();
            return;

        } else if (newPasswordConfirm.trim() === "") {
            alert("새 비밀번호를 다시 입력해주세요");
            newPasswordConfirmRef.current?.focus();
            return;
        }
        if (newPassword !== newPasswordConfirm) {
            alert("새 비밀번호가 일치하지 않습니다.");
            newPasswordConfirmRef.current?.focus();
            return;
        }


        try {
            await axios.post("/api/auth/password", {
                password,
                newPassword,
                newPasswordConfirm,
            });

            alert("비밀번호가 변경되었습니다.");

            // ✅ 메인페이지로 이동
            navigate("/");
        } catch (error) {
            console.error(error.response?.data);
            alert(error.response?.data?.message || "비밀번호 변경 실패");
        }
    };

    return (
        <RegisterWrapper onSubmit={handleSubmit}>
            <MyPageText>
                비밀번호 변경
            </MyPageText>

            <LoginSection>
                {/* 비밀번호 입력 */}
                <LoginInput
                    ref={passwordRef}
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="기존 비밀번호를 입력해주세요"
                />

                {/*새 비밀번호 입력 */}
                <LoginInput
                    ref={newPasswordRef}
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="새 비밀번호를 입력해주세요"
                />



                {/*새 비밀번호 다시 입력 */}
                <LoginInput
                    ref={newPasswordConfirmRef}
                    type="password"
                    value={newPasswordConfirm}
                    onChange={(e) => setNewPasswordConfirm(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={inputFocused ? theme[season].focusColor : theme[season].borderColor}
                    onFocus={handleFocus}
                    onBlur={handleBlur}
                    placeholder="새 비밀번호를 다시 입력해주세요"
                />





                {/* 회원가입 버튼 */}
                <LoginButton
                    type="submit"
                    style={{ marginBottom: '20px' }}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    변경하기
                </LoginButton>
            </LoginSection>
        </RegisterWrapper>
    );
};

export default MyPagePassword;

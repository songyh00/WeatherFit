import React, { useState, useEffect } from "react";
import {
    LoginWrapper,
    LoginSection,
    LoginInput,
    LoginButton,
    LoginTexts,
    JoinTheMembershipLink
} from "../layout/login.style.js";
import { Link, useNavigate } from "react-router-dom";
import logo from '../assets/logo.png';
import { getSeason, theme } from "../components/theme.js";
import axios from 'axios';

const Login = () => {
    const [season, setSeason] = useState(getSeason());

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const handleLogin = async (event) => {
        event.preventDefault();  // ✅ 폼 새로고침 막기
        console.log('로그인 시도!');

        try {
            const response = await axios.post('/api/auth/login', {
                username: username,
                password: password,
            });

            const { token, username: loggedInUsername } = response.data;
            localStorage.setItem('token', token);
            localStorage.setItem('username', loggedInUsername);

            console.log('로그인 성공, 토큰:', token);

            navigate('/'); // 로그인 성공 시 메인 페이지로 이동
        } catch (error) {
            console.error('로그인 실패', error.response?.data);
            alert(error.response?.data?.message || '로그인 실패');
        }
    };

    useEffect(() => {
        const interval = setInterval(() => {
            setSeason(getSeason());
        }, 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);

    return (
        <LoginWrapper>
            <Link to="/">
                <img src={logo} alt="WeatherFit Logo" />
            </Link>
            <LoginSection>
                <LoginInput
                    style={{ marginTop: '20px' }}
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    placeholder="아이디를 입력해주세요"
                    autoComplete="username"
                />
                <LoginInput
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    placeholder="비밀번호를 입력해주세요"
                    autoComplete="current-password"
                />
                <LoginButton
                    type="submit"
                    onClick={handleLogin}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $textColor={theme[season].textColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    Login
                </LoginButton>
                <LoginTexts>
                    <JoinTheMembershipLink to="../JoinTheMembership">회원가입</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="../ForgotIdOrPassword?page=id">| 아이디 찾기</JoinTheMembershipLink>
                    <JoinTheMembershipLink to="../ForgotIdOrPassword?page=pw">| 비밀번호 찾기</JoinTheMembershipLink>
                </LoginTexts>
            </LoginSection>
        </LoginWrapper>
    );
};

export default Login;

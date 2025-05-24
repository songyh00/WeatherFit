import '../App.css';
import {
    ContentsWrapper,
    Card,
    Container,
    Title,
    Input,
    Button,
    RadioGroup,
    RadioLabel
} from "../layout/Mypage_info.style.js";
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getSeason, theme } from "../components/theme.js";

const ProfileEditor = () => {
    const [email, setEmail] = useState('');
    const [gender, setGender] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const [season, setSeason] = useState(getSeason());

    useEffect(() => {
        fetch('/api/user/profile', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => {
                if (!res.ok) throw new Error("사용자 정보 조회 실패");
                return res.json();
            })
            .then(data => {
                setEmail(data.email);
                setGender(data.gender);
            })
            .catch(err => {
                console.error("사용자 정보 불러오기 실패", err);
            });
    }, []);

    const handleSubmit = () => {
        setError("");

        const dto = { email, gender };

        fetch('/api/user/change-profile', {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(dto)
        })
            .then(res => {
                if (res.status === 409) throw new Error("이미 사용 중인 이메일입니다.");
                if (!res.ok) throw new Error("프로필 수정에 실패했습니다.");
                alert("프로필이 수정되었습니다.");
                navigate("/");
            })
            .catch(err => {
                setError(err.message);
                console.error("수정 오류:", err);
            });
    };

    useEffect(() => {
        const interval = setInterval(() => setSeason(getSeason()), 1000 * 60 * 60 * 24);
        return () => clearInterval(interval);
    }, []);

    return (
        <ContentsWrapper>
            <Card>
                <Title>내 정보 수정</Title>
                <Input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    placeholder="이메일을 입력해주세요"
                />
                <RadioGroup>
                    <RadioLabel>
                        <input
                            type="radio"
                            value="남자"
                            checked={gender === '남자'}
                            onChange={(e) => setGender(e.target.value)}
                        /> 남자
                    </RadioLabel>
                    <RadioLabel>
                        <input
                            type="radio"
                            value="여자"
                            checked={gender === '여자'}
                            onChange={(e) => setGender(e.target.value)}
                        /> 여자
                    </RadioLabel>
                </RadioGroup>
                {error && <div style={{ color: "red", marginBottom: "10px" }}>{error}</div>}
                <Button
                    onClick={handleSubmit}
                    $borderColor={theme[season].borderColor}
                    $bgColor={theme[season].bgColor}
                    $focusColor={theme[season].focusColor}
                    $hoverBgColor={theme[season].focusColor}
                >
                    저장
                </Button>
            </Card>
        </ContentsWrapper>
    );
};

const MyPageInfo = () => {
    return (
        <Container>
            <ProfileEditor />
        </Container>
    );
};

export default MyPageInfo;

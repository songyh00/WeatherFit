import '../App.css';
import {
    ContentsWrapper,
    Card,
    Container,
    Title,
    Input,
    Button
} from "../layout/Mypage_info.style.js";
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const ProfileEditor = () => {
    const [email, setEmail] = useState('');
    const [gender, setGender] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    // 🔹 기존 사용자 정보 가져오기
    useEffect(() => {
        fetch('/api/user/profile', {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`
            }
        })
            .then(res => {
                if (!res.ok) {
                    throw new Error("사용자 정보 조회 실패");
                }
                return res.json();
            })
            .then(data => {
                setEmail(data.email);
                setGender(data.gender); // "남자" 또는 "여자"
            })
            .catch(err => {
                console.error("사용자 정보 불러오기 실패", err);
            });
    }, []);

    // 🔹 저장 처리
    const handleSubmit = () => {
        setError("");

        const dto = {
            email: email,
            gender: gender // 그대로 "남자"/"여자" 전송
        };

        fetch('/api/user/change-profile', {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify(dto)
        })
            .then(res => {
                if (res.status === 409) {
                    throw new Error("이미 사용 중인 이메일입니다.");
                }
                if (!res.ok) {
                    throw new Error("프로필 수정에 실패했습니다.");
                }

                alert("프로필이 수정되었습니다.");
                navigate("/MyPage");
            })
            .catch(err => {
                setError(err.message);
                console.error("수정 오류:", err);
            });
    };

    return (
        <ContentsWrapper>
            <Card>
                <Title>내 정보 수정</Title>

                <Input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    placeholder="이메일"
                />

                <div style={{ margin: '10px 0' }}>
                    <label style={{ marginRight: '10px' }}>
                        <input
                            type="radio"
                            value="남자"
                            checked={gender === '남자'}
                            onChange={(e) => setGender(e.target.value)}
                        />
                        남성
                    </label>
                    <label>
                        <input
                            type="radio"
                            value="여자"
                            checked={gender === '여자'}
                            onChange={(e) => setGender(e.target.value)}
                        />
                        여성
                    </label>
                </div>

                {error && <div style={{ color: "red", marginBottom: "10px" }}>{error}</div>}

                <Button onClick={handleSubmit}>저장</Button>
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

import '../App.css';
import {
    ContentsWrapper,
    Card,
    Container,
    Title,
    Input,
    Button
} from "../layout/Mypage_info.style.js";
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const ProfileEditor = () => {
    const [password, setPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [newPasswordConfirm, setNewPasswordConfirm] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = () => {
        setError("");

        if (newPassword !== newPasswordConfirm) {
            setError("새 비밀번호가 일치하지 않습니다.");
            return;
        }

        fetch('/api/auth/change-password', {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`
            },
            body: JSON.stringify({
                oldPassword: password,
                newPassword: newPassword,
                newPasswordConfirm: newPasswordConfirm
            })
        })
            .then(res => {
                if (!res.ok) {
                    return res.json().then(data => {
                        throw new Error(data.message || "비밀번호 변경에 실패했습니다.");
                    });
                }
                alert("비밀번호가 성공적으로 변경되었습니다.");
                navigate("/MyPage");
            })
            .catch(err => {
                console.error("변경 오류:", err);
                setError(err.message);
            });
    };

    return (
        <ContentsWrapper>
            <Card>
                <Title>비밀번호 변경</Title>

                <Input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="기존 비밀번호"
                />
                <Input
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    placeholder="새 비밀번호"
                />
                <Input
                    type="password"
                    value={newPasswordConfirm}
                    onChange={(e) => setNewPasswordConfirm(e.target.value)}
                    placeholder="새 비밀번호 확인"
                />

                {error && <div style={{ color: "red", marginBottom: "10px" }}>{error}</div>}

                <Button onClick={handleSubmit}>저장</Button>
            </Card>
        </ContentsWrapper>
    );
};

const MyPagePassword = () => {
    return (
        <Container>
            <ProfileEditor />
        </Container>
    );
};

export default MyPagePassword;

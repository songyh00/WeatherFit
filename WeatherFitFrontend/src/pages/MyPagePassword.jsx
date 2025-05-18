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


const ProfileEditor = () => {
    const [password, setPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [newPasswordConfirm, setNewPasswordConfirm] = useState('');

    const handleSubmit = () => {
        if (newPassword !== newPasswordConfirm) {
            alert('새 비밀번호가 일치하지 않습니다.');
            return;
        }

        alert('비밀번호가 변경되었습니다.');
        // 여기에 API 호출 등의 로직 추가 가능
    };

    return (
        <ContentsWrapper>
            <Card>
                <Title>내 정보 수정</Title>
                <Input
                    type="text"
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
                
                

                <Button onClick={handleSubmit}>저장</Button>
            </Card>



        </ContentsWrapper>
    );
};

const MyPagePassword = () => {


    // const [wishlist, setWishlist] = useState([]);

    // useEffect(() => {
    //     // 실제 데이터는 API로 대체
    //     setWishlist(['레트로 체크 자켓', '풀오버 후드티', '빈티지 청바지', '집업 셔츠']);
    // }, []);

    return (
        <Container>
            <ProfileEditor />
        </Container>
    );
}

export default MyPagePassword;

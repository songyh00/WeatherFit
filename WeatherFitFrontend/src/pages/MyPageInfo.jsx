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
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [gender, setGender] = useState('남성');

    const handleSubmit = () => {
        alert(`이름: ${name}, 이메일: ${email}, 성별: ${gender}이 변경되었습니다.`);
        // 여기에 API 호출 등의 로직 추가 가능
    };

    return (
        <ContentsWrapper>
            <Card>
                <Title>내 정보 수정</Title>
                <Input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="이름"
                />
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
                            value="남성"
                            checked={gender === '남성'}
                            onChange={(e) => setGender(e.target.value)}
                        />
                        남성
                    </label>
                    <label>
                        <input
                            type="radio"
                            value="여성"
                            checked={gender === '여성'}
                            onChange={(e) => setGender(e.target.value)}
                        />
                        여성
                    </label>
                </div>

                <Button onClick={handleSubmit}>저장</Button>
            </Card>



        </ContentsWrapper>
    );
};

const MyPageInfo = () => {


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

export default MyPageInfo;

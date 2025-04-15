import styled from 'styled-components';

export const ContentsWrapper = styled.div`
    padding: 176px 0 0 0;
    width: 80%;
    margin: 30px auto 0 auto;
`;

export const Container = styled.div`
  max-width: 960px;
  margin: 0 auto;
  padding: 2rem;
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

export const Card = styled.div`
  background: #fff;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
`;

export const Title = styled.h2`
  margin-bottom: 1rem;
  font-size: 1.5rem;
`;

export const Product = styled.div`
  padding: 0.5rem 0;
  border-bottom: 1px solid #eee;
`;

export const Input = styled.input`
  width: 100%;
  padding: 0.5rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 6px;
`;

export const Button = styled.button`
  padding: 0.6rem 1.2rem;
  background-color: #0070f3;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;

  &:hover {
    background-color: #0056c1;
  }
`;
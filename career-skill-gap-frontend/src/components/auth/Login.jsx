import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function Login() {
    const navigate = useNavigate();
    const { login } = useAuth();
    const [formData, setFormData] = useState({ email: '', password: '' });
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setLoading(true);

        try {
            await login(formData);
            navigate('/dashboard');
        } catch (err) {
            setError(err.response?.data?.message || 'Login failed. Please check your credentials.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="page">
            <div className="container">
                <div style={{ maxWidth: '450px', margin: '0 auto' }}>
                    <div className="card fade-in">
                        <div className="card-header text-center">
                            <h1 className="card-title" style={{ fontSize: '2rem' }}>🎯 SkillGap AI</h1>
                            <p className="card-subtitle">AI-Powered Career Skill Analysis</p>
                        </div>

                        <form onSubmit={handleSubmit}>
                            {error && <div className="alert alert-error">{error}</div>}

                            <div className="form-group">
                                <label className="form-label">Email</label>
                                <input
                                    type="email"
                                    className="form-input"
                                    placeholder="your@email.com"
                                    value={formData.email}
                                    onChange={(e) => setFormData({ ...formData, email: e.target.value })}
                                    required
                                />
                            </div>

                            <div className="form-group">
                                <label className="form-label">Password</label>
                                <input
                                    type="password"
                                    className="form-input"
                                    placeholder="••••••••"
                                    value={formData.password}
                                    onChange={(e) => setFormData({ ...formData, password: e.target.value })}
                                    required
                                />
                            </div>

                            <button type="submit" className="btn btn-primary btn-full" disabled={loading}>
                                {loading ? 'Logging in...' : 'Login'}
                            </button>
                        </form>

                        <p className="text-center mt-3" style={{ color: 'var(--text-secondary)' }}>
                            Don't have an account? <Link to="/register" style={{ color: 'var(--primary-light)' }}>Register</Link>
                        </p>


                    </div>
                </div>
            </div>
        </div>
    );
}

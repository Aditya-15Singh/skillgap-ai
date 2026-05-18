import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function Dashboard() {
    const { user } = useAuth();

    return (
        <div className="page">
            <div className="container">
                <div className="hero">
                    <h1 className="hero-title slide-up" style={{
                        fontSize: '3.5rem',
                        fontWeight: '900',
                        background: 'linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%)',
                        WebkitBackgroundClip: 'text',
                        WebkitTextFillColor: 'transparent',
                        backgroundClip: 'text',
                        textShadow: '0 0 40px rgba(102, 126, 234, 0.3)',
                        animationDelay: '0.1s'
                    }}>
                        Welcome, {user && user.name ? user.name.split(' ')[0] : 'Student'}! 👋
                    </h1>
                    <p className="hero-subtitle slide-up" style={{
                        color: '#64748b',
                        fontSize: '1.25rem',
                        fontWeight: '500',
                        marginTop: '1rem',
                        animationDelay: '0.2s'
                    }}>
                        Ready to analyze your skill gaps and plan your career growth?
                    </p>
                </div>

                {/* Learning Progress Widget */}
                <div className="card mt-4 mb-4 fade-in hover-scale" style={{
                    background: 'white',
                    border: '1px solid #e2e8f0',
                    borderLeft: '4px solid #4facfe',
                    animationDelay: '0.3s'
                }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
                        <div>
                            <h3 style={{ fontSize: '1.1rem', fontWeight: '700', color: '#1e293b', marginBottom: '0.25rem' }}>
                                Your Learning Progress
                            </h3>
                            <p style={{ color: '#64748b', fontSize: '0.9rem', marginBottom: 0 }}>
                                Track your completed courses and tutorials
                            </p>
                        </div>
                        <Link to="/roadmap" className="btn btn-primary" style={{ padding: '0.5rem 1rem', fontSize: '0.9rem' }}>
                            View Roadmap & Update Progress →
                        </Link>
                    </div>
                </div>

                <div className="grid grid-3 mt-4">
                    <Link to="/profile-setup" className="card hover-scale" style={{
                        textDecoration: 'none',
                        background: 'linear-gradient(135deg, #667eea 20%, #a78bfa 100%)',
                        border: 'none',
                        boxShadow: '0 8px 32px rgba(102, 126, 234, 0.4)',
                        transition: 'all 0.3s ease'
                    }}>
                        <div className="card-header">
                            <div style={{ fontSize: '4rem', textAlign: 'center', filter: 'drop-shadow(0 4px 8px rgba(0,0,0,0.3))' }}>📝</div>
                            <h3 className="card-title text-center" style={{
                                color: 'white',
                                fontSize: '1.5rem',
                                fontWeight: '700',
                                textShadow: '0 2px 4px rgba(0,0,0,0.2)'
                            }}>Profile Setup</h3>
                            <p className="card-subtitle text-center" style={{
                                color: 'rgba(255,255,255,0.95)',
                                fontSize: '1rem'
                            }}>
                                Add your skills and career goals
                            </p>
                        </div>
                    </Link>

                    <Link to="/skill-gap" className="card hover-scale" style={{
                        textDecoration: 'none',
                        background: 'linear-gradient(135deg, #f093fb 20%, #f5576c 100%)',
                        border: 'none',
                        boxShadow: '0 8px 32px rgba(240, 147, 251, 0.4)',
                        transition: 'all 0.3s ease'
                    }}>
                        <div className="card-header">
                            <div style={{ fontSize: '4rem', textAlign: 'center', filter: 'drop-shadow(0 4px 8px rgba(0,0,0,0.3))' }}>🎯</div>
                            <h3 className="card-title text-center" style={{
                                color: 'white',
                                fontSize: '1.5rem',
                                fontWeight: '700',
                                textShadow: '0 2px 4px rgba(0,0,0,0.2)'
                            }}>Skill Gap Analysis</h3>
                            <p className="card-subtitle text-center" style={{
                                color: 'rgba(255,255,255,0.95)',
                                fontSize: '1rem'
                            }}>
                                See what skills you need to learn
                            </p>
                        </div>
                    </Link>

                    <Link to="/roadmap" className="card hover-scale" style={{
                        textDecoration: 'none',
                        background: 'linear-gradient(135deg, #4facfe 20%, #00f2fe 100%)',
                        border: 'none',
                        boxShadow: '0 8px 32px rgba(79, 172, 254, 0.4)',
                        transition: 'all 0.3s ease'
                    }}>
                        <div className="card-header">
                            <div style={{ fontSize: '4rem', textAlign: 'center', filter: 'drop-shadow(0 4px 8px rgba(0,0,0,0.3))' }}>🗺️</div>
                            <h3 className="card-title text-center" style={{
                                color: 'white',
                                fontSize: '1.5rem',
                                fontWeight: '700',
                                textShadow: '0 2px 4px rgba(0,0,0,0.2)'
                            }}>Learning Roadmap</h3>
                            <p className="card-subtitle text-center" style={{
                                color: 'rgba(255,255,255,0.95)',
                                fontSize: '1rem'
                            }}>
                                Get your personalized learning path
                            </p>
                        </div>
                    </Link>
                </div>

                <div className="mt-4">
                    <h2 className="text-center mb-4" style={{
                        fontSize: '2rem',
                        fontWeight: '800',
                        background: 'linear-gradient(135deg, #a78bfa 0%, #f093fb 100%)',
                        WebkitBackgroundClip: 'text',
                        WebkitTextFillColor: 'transparent',
                        backgroundClip: 'text'
                    }}>How It Works</h2>

                    <div className="grid grid-3">
                        <div className="card fade-in" style={{
                            background: 'white',
                            border: '1px solid #e2e8f0',
                            boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
                            textAlign: 'center',
                            padding: '2rem'
                        }}>
                            <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🚀</div>
                            <h3 style={{
                                fontSize: '1.25rem',
                                fontWeight: '700',
                                marginBottom: '0.5rem',
                                color: '#4f46e5'
                            }}>Step 1: Set Up Profile</h3>
                            <p style={{ color: '#64748b' }}>
                                Enter your current skills, skill levels, and your target career role
                            </p>
                        </div>

                        <div className="card fade-in" style={{
                            background: 'white',
                            border: '1px solid #e2e8f0',
                            boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
                            textAlign: 'center',
                            padding: '2rem',
                            animationDelay: '0.1s'
                        }}>
                            <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>🤖</div>
                            <h3 style={{
                                fontSize: '1.25rem',
                                fontWeight: '700',
                                marginBottom: '0.5rem',
                                color: '#ec4899'
                            }}>Step 2: AI Analysis</h3>
                            <p style={{ color: '#64748b' }}>
                                Our AI analyzes the gap between your current skills and required skills
                            </p>
                        </div>

                        <div className="card fade-in" style={{
                            background: 'white',
                            border: '1px solid #e2e8f0',
                            boxShadow: '0 4px 6px -1px rgba(0, 0, 0, 0.1)',
                            textAlign: 'center',
                            padding: '2rem',
                            animationDelay: '0.2s'
                        }}>
                            <div style={{ fontSize: '3rem', marginBottom: '1rem' }}>📚</div>
                            <h3 style={{
                                fontSize: '1.25rem',
                                fontWeight: '700',
                                marginBottom: '0.5rem',
                                color: '#0ea5e9'
                            }}>Step 3: Get Roadmap</h3>
                            <p style={{ color: '#64748b' }}>
                                Receive a personalized learning path with courses, tutorials, and practice resources
                            </p>
                        </div>
                    </div>
                </div>

                <footer style={{
                    marginTop: '4rem',
                    padding: '2rem 0',
                    borderTop: '1px solid #e2e8f0',
                    textAlign: 'center',
                    color: '#94a3b8',
                    fontSize: '0.875rem'
                }}>
                    <p>All rights goes to Ayush &copy; {new Date().getFullYear()}</p>
                </footer>
            </div>
        </div>
    );
}

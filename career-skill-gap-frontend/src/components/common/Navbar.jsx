import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function Navbar() {
    const { user, logout } = useAuth();

    return (
        <nav className="navbar">
            <div className="container navbar-container">
                <Link to="/dashboard" className="navbar-brand">
                    🎯 SkillGap AI
                </Link>
                <div className="navbar-menu">
                    <Link to="/dashboard" className="navbar-link">Dashboard</Link>
                    <Link to="/skill-gap" className="navbar-link">Analysis</Link>
                    <Link to="/roadmap" className="navbar-link">Roadmap</Link>
                    <span className="navbar-link" style={{ color: 'var(--text-tertiary)' }}>
                        {user?.name}
                    </span>
                    <button onClick={logout} className="btn btn-secondary">
                        Logout
                    </button>
                </div>
            </div>
        </nav>
    );
}

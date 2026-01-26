import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { analysisService } from '../../services/apiService';

export default function SkillGapAnalysis() {
    const [loading, setLoading] = useState(true);
    const [analysis, setAnalysis] = useState(null);

    useEffect(() => {
        loadAnalysis();
    }, []);

    const loadAnalysis = async () => {
        try {
            const data = await analysisService.getSkillGap();
            setAnalysis(data);
        } catch (error) {
            console.error('Error loading analysis:', error);
        } finally {
            setLoading(false);
        }
    };

    if (loading) {
        return <div className="page"><div className="spinner"></div></div>;
    }

    if (!analysis) {
        return (
            <div className="page">
                <div className="container">
                    <div className="card">
                        <div className="alert alert-info">
                            Please complete your profile setup first.
                            <Link to="/profile-setup" className="btn btn-primary mt-2">Go to Profile Setup</Link>
                        </div>
                    </div>
                </div>
            </div>
        );
    }

    return (
        <div className="page">
            <div className="container">
                <div className="card fade-in">
                    <div className="card-header">
                        <h1 className="card-title">AI Skill Gap Analysis</h1>
                        <p className="card-subtitle">Target Role: {analysis.careerRole}</p>
                    </div>

                    {/* Overall Match */}
                    <div className="stat-card mb-4">
                        <div className="stat-value">{analysis.overallMatchPercentage}%</div>
                        <div className="stat-label">Overall Match</div>
                        <div className="progress-bar mt-2">
                            <div className="progress-fill" style={{ width: `${analysis.overallMatchPercentage}%` }}></div>
                        </div>
                    </div>

                    {/* Missing Skills */}
                    {analysis.missingSkills.length > 0 && (
                        <div className="mb-4">
                            <h2 className="card-title mb-3">❌ Missing Skills</h2>
                            <div className="grid grid-2">
                                {analysis.missingSkills.map((skill, index) => (
                                    <div key={index} className="card">
                                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start' }}>
                                            <div>
                                                <h3 style={{ color: 'var(--primary-light)', marginBottom: '0.5rem' }}>
                                                    {skill.skillName}
                                                </h3>
                                                <div className="mt-2">
                                                    <span className="badge badge-danger">Required: {skill.requiredLevel}</span>
                                                    <span className="badge badge-warning ml-2">{skill.importance}</span>
                                                </div>
                                            </div>
                                            <div className="badge badge-danger" style={{ fontSize: '1.25rem' }}>
                                                Gap: {skill.gapScore}
                                            </div>
                                        </div>
                                        {skill.prerequisites.length > 0 && (
                                            <div className="mt-2" style={{ fontSize: '0.875rem', color: 'var(--text-tertiary)' }}>
                                                Prerequisites: {skill.prerequisites.join(', ')}
                                            </div>
                                        )}
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}

                    {/* Skills to Improve */}
                    {analysis.skillsToImprove.length > 0 && (
                        <div className="mb-4">
                            <h2 className="card-title mb-3">⚠️ Skills to Improve</h2>
                            <div className="grid grid-2">
                                {analysis.skillsToImprove.map((skill, index) => (
                                    <div key={index} className="card">
                                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start' }}>
                                            <div>
                                                <h3 style={{ color: 'var(--primary-light)', marginBottom: '0.5rem' }}>
                                                    {skill.skillName}
                                                </h3>
                                                <div className="mt-2">
                                                    <span className="badge badge-warning">Current: {skill.currentLevel}</span>
                                                    <span className="badge badge-success ml-2">Target: {skill.requiredLevel}</span>
                                                </div>
                                            </div>
                                            <div className="badge badge-warning" style={{ fontSize: '1.25rem' }}>
                                                Gap: {skill.gapScore}
                                            </div>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    )}

                    <Link to="/roadmap" className="btn btn-success btn-full mt-4">
                        View Learning Roadmap →
                    </Link>
                </div>
            </div>
        </div>
    );
}

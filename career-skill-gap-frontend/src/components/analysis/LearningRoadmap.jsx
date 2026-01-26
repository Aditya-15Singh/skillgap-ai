import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { analysisService, progressService } from '../../services/apiService';

export default function LearningRoadmap() {
    const [loading, setLoading] = useState(true);
    const [roadmap, setRoadmap] = useState(null);
    const [completedResources, setCompletedResources] = useState([]);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        try {
            const [roadmapData, completedData] = await Promise.all([
                analysisService.getLearningRoadmap(),
                progressService.getCompletedResources()
            ]);
            setRoadmap(roadmapData);
            setCompletedResources(completedData);
        } catch (error) {
            console.error('Error loading roadmap data:', error);
        } finally {
            setLoading(false);
        }
    };

    const toggleResource = async (resourceId) => {
        // Optimistic update
        const isCurrentlyCompleted = completedResources.includes(resourceId);

        let newCompletedList;
        if (isCurrentlyCompleted) {
            newCompletedList = completedResources.filter(id => id !== resourceId);
        } else {
            newCompletedList = [...completedResources, resourceId];
        }
        setCompletedResources(newCompletedList);

        try {
            await progressService.toggleProgress(resourceId);
        } catch (error) {
            console.error('Error toggling progress, reverting:', error);
            // Revert on failure
            setCompletedResources(completedResources);
        }
    };

    if (loading) {
        return <div className="page"><div className="spinner"></div></div>;
    }

    if (!roadmap) {
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

    // Calculate progress statistics
    const totalResources = roadmap.phases.reduce((acc, phase) =>
        acc + phase.skills.reduce((acc2, skill) => acc2 + skill.resources.length, 0), 0);
    const completedCount = completedResources.length;
    const progressPercentage = totalResources > 0 ? Math.round((completedCount / totalResources) * 100) : 0;

    return (
        <div className="page">
            <div className="container">
                <div className="card fade-in mb-4">
                    <div className="card-header">
                        <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '1rem' }}>
                            <div>
                                <h1 className="card-title">🗺️ Personalized Learning Roadmap</h1>
                                <p className="card-subtitle">Career Path: {roadmap.careerRole}</p>
                            </div>
                            <div className="card" style={{ padding: '1rem', background: 'var(--bg-secondary)', minWidth: '200px' }}>
                                <div style={{ fontSize: '0.875rem', color: 'var(--text-secondary)', marginBottom: '0.5rem' }}>
                                    Overall Progress
                                </div>
                                <div style={{ display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                                    <div style={{ flex: 1, height: '8px', background: 'var(--border)', borderRadius: '4px', overflow: 'hidden' }}>
                                        <div style={{
                                            width: `${progressPercentage}%`,
                                            height: '100%',
                                            background: 'var(--success)',
                                            transition: 'width 0.5s ease-out'
                                        }}></div>
                                    </div>
                                    <span style={{ fontWeight: 'bold', color: 'var(--success)' }}>{progressPercentage}%</span>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="stat-card mb-4">
                        <div className="stat-value">{roadmap.estimatedWeeks}</div>
                        <div className="stat-label">Estimated Weeks to Complete</div>
                    </div>

                    <div className="timeline">
                        {roadmap.phases.map((phase) => (
                            <div key={phase.phaseNumber} className="timeline-item">
                                <div className="card">
                                    <h2 className="card-title">
                                        Phase {phase.phaseNumber}: {phase.phaseName}
                                    </h2>
                                    <p className="card-subtitle">Estimated: {phase.estimatedWeeks} weeks</p>

                                    <div className="mt-3">
                                        {phase.skills.map((skill, index) => (
                                            <div key={index} className="card mb-3" style={{ background: 'var(--bg-card)' }}>
                                                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'start' }}>
                                                    <div>
                                                        <h3 style={{ color: 'var(--primary-light)', fontSize: '1.1rem' }}>
                                                            {skill.skillName}
                                                        </h3>
                                                        <span className="badge badge-success mt-1">
                                                            Target: {skill.targetLevel}
                                                        </span>
                                                    </div>
                                                    <div className="badge badge-primary">
                                                        Priority: {skill.priority}
                                                    </div>
                                                </div>

                                                {skill.resources.length > 0 && (
                                                    <div className="mt-3">
                                                        <h4 style={{ fontSize: '0.9rem', color: 'var(--text-secondary)', marginBottom: '0.75rem' }}>
                                                            📚 Learning Resources:
                                                        </h4>
                                                        <div style={{ display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                                                            {skill.resources.map((resource, rIndex) => {
                                                                const isCompleted = completedResources.includes(resource.id);
                                                                return (
                                                                    <div key={rIndex} className="card" style={{
                                                                        background: isCompleted ? 'rgba(16, 185, 129, 0.05)' : 'var(--bg-hover)',
                                                                        padding: '0.75rem',
                                                                        border: isCompleted ? '1px solid var(--success)' : '1px solid transparent',
                                                                        transition: 'all 0.2s'
                                                                    }}>
                                                                        <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                                                                            <input
                                                                                type="checkbox"
                                                                                checked={isCompleted}
                                                                                onChange={() => toggleResource(resource.id)}
                                                                                style={{ width: '1.25rem', height: '1.25rem', cursor: 'pointer' }}
                                                                            />
                                                                            <a
                                                                                href={resource.url}
                                                                                target="_blank"
                                                                                rel="noopener noreferrer"
                                                                                style={{
                                                                                    textDecoration: 'none',
                                                                                    flex: 1,
                                                                                    display: 'flex',
                                                                                    justifyContent: 'space-between',
                                                                                    alignItems: 'center'
                                                                                }}
                                                                            >
                                                                                <div>
                                                                                    <div style={{
                                                                                        fontWeight: 600,
                                                                                        color: 'var(--text-primary)',
                                                                                        marginBottom: '0.25rem',
                                                                                        textDecoration: isCompleted ? 'line-through' : 'none'
                                                                                    }}>
                                                                                        {resource.title}
                                                                                    </div>
                                                                                    <div style={{ fontSize: '0.75rem', color: 'var(--text-tertiary)' }}>
                                                                                        {resource.platform} • {resource.type}
                                                                                    </div>
                                                                                </div>
                                                                                {resource.isFree && (
                                                                                    <span className="badge badge-success">FREE</span>
                                                                                )}
                                                                            </a>
                                                                        </div>
                                                                    </div>
                                                                );
                                                            })}
                                                        </div>
                                                    </div>
                                                )}
                                            </div>
                                        ))}
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>

                    <div className="mt-4 alert alert-success">
                        ✅ Your personalized roadmap is ready! Mark items as done to track your progress.
                    </div>
                </div>
            </div>
        </div>
    );
}

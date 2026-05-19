import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { profileService, publicService, careerGoalService } from '../../services/apiService';

export default function ProfileSetup() {
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [roles, setRoles] = useState([]);
    const [skills, setSkills] = useState([]);
    const [existingGoal, setExistingGoal] = useState(null);
    const [formData, setFormData] = useState({
        careerGoal: '',
        studyHoursPerWeek: 10,
        experienceLevel: 'BEGINNER',
        currentSkills: []
    });

    useEffect(() => {
        loadData();
    }, []);

    // Default data in case API fails or is empty
    const DEFAULT_ROLES = [
        { id: '1', name: 'Frontend Developer' },
        { id: '2', name: 'Backend Developer' },
        { id: '3', name: 'Full Stack Developer' },
        { id: '4', name: 'DevOps Engineer' },
        { id: '5', name: 'Data Scientist' },
        { id: '6', name: 'Machine Learning Engineer' },
        { id: '7', name: 'Mobile App Developer (iOS)' },
        { id: '8', name: 'Mobile App Developer (Android)' },
        { id: '9', name: 'UI/UX Designer' },
        { id: '10', name: 'Cloud Architect' },
        { id: '11', name: 'Cybersecurity Analyst' },
        { id: '12', name: 'Game Developer' },
        { id: '13', name: 'Blockchain Developer' },
        { id: '14', name: 'QA Engineer' },
        { id: '15', name: 'Product Manager' },
        { id: '16', name: 'Database Administrator' },
        { id: '17', name: 'Network Engineer' },
        { id: '18', name: 'Systems Administrator' },
        { id: '19', name: 'AI Research Scientist' },
        { id: '20', name: 'Technical Lead' }
    ];

    const DEFAULT_SKILLS = [
        { id: '1', name: 'Java' },
        { id: '2', name: 'Python' },
        { id: '3', name: 'JavaScript' },
        { id: '4', name: 'React' },
        { id: '5', name: 'Node.js' },
        { id: '6', name: 'HTML' },
        { id: '7', name: 'CSS' },
        { id: '8', name: 'SQL' },
        { id: '9', name: 'Docker' },
        { id: '10', name: 'AWS' },
        { id: '11', name: 'Git' },
        { id: '12', name: 'TypeScript' },
        { id: '13', name: 'C++' },
        { id: '14', name: 'C#' },
        { id: '15', name: 'Go' },
        { id: '16', name: 'Angular' },
        { id: '17', name: 'Vue.js' },
        { id: '18', name: 'MongoDB' },
        { id: '19', name: 'Kubernetes' },
        { id: '20', name: 'Swift' }
    ];

    const loadData = async () => {
        try {
            const [rolesData, skillsData, goalData] = await Promise.all([
                publicService.getAllRoles().catch(() => []),
                publicService.getAllSkills().catch(() => []),
                careerGoalService.getGoal().catch(() => null)
            ]);

            setRoles(rolesData && rolesData.length > 0 ? rolesData : DEFAULT_ROLES);
            setSkills(skillsData && skillsData.length > 0 ? skillsData : DEFAULT_SKILLS);

            if (goalData) {
                setExistingGoal(goalData);
                setFormData(prev => ({ ...prev, careerGoal: goalData.goalName }));
            }
        } catch (error) {
            console.error('Error loading data, using defaults:', error);
            setRoles(DEFAULT_ROLES);
            setSkills(DEFAULT_SKILLS);
        }
    };

    const toggleSkill = (skillName, level) => {
        const existingIndex = formData.currentSkills.findIndex(s => s.skillName === skillName);
        const newSkills = [...formData.currentSkills];

        if (existingIndex >= 0) {
            if (newSkills[existingIndex].skillLevel === level) {
                newSkills.splice(existingIndex, 1);
            } else {
                newSkills[existingIndex].skillLevel = level;
            }
        } else {
            newSkills.push({ skillName, skillLevel: level });
        }

        setFormData({ ...formData, currentSkills: newSkills });
    };

    const handleDeleteGoal = async () => {
        if (!window.confirm('Are you sure you want to delete your career goal? This will reset your roadmap progress.')) {
            return;
        }
        try {
            setLoading(true);
            await careerGoalService.deleteGoal();
            setExistingGoal(null);
            setFormData(prev => ({ ...prev, careerGoal: '' }));
            alert('Career goal deleted. You can now select a new one.');
        } catch (error) {
            console.error("Error deleting goal:", error);
            alert("Failed to delete goal.");
        } finally {
            setLoading(false);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            // If no existing goal, create one first.
            if (!existingGoal) {
                try {
                    await careerGoalService.createGoal({ goalName: formData.careerGoal });
                } catch (goalError) {
                    // Ignore conflict if it already exists (e.g. from a previous partial submit)
                    if (goalError.response?.status !== 409) {
                        throw goalError;
                    }
                }
            } else if (existingGoal.goalName !== formData.careerGoal) {
                // Should not happen if UI is disabled, but safety check
                alert("You already have a goal. Please delete it first.");
                setLoading(false);
                return;
            }

            await profileService.createOrUpdateProfile(formData);
            
            // Navigate back to the dashboard once profile is completely setup
            navigate('/dashboard');
        } catch (error) {
            alert('Error saving profile: ' + (error.response?.data?.message || error.message));
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="page">
            <div className="container">
                <div className="card fade-in">
                    <div className="card-header">
                        <h1 className="card-title">Profile Setup</h1>
                        <p className="card-subtitle">Tell us about your current skills and career goals</p>
                    </div>

                    <form onSubmit={handleSubmit}>
                        <div className="form-group">
                            <label className="form-label">Career Goal</label>
                            {existingGoal ? (
                                <div className="alert alert-info" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                    <span>
                                        <strong>Current Goal:</strong> {existingGoal.goalName}
                                        <div style={{ fontSize: '0.85rem', marginTop: '4px' }}>
                                            You must delete your current goal before selecting a new one.
                                        </div>
                                    </span>
                                    <button
                                        type="button"
                                        className="btn btn-danger"
                                        onClick={handleDeleteGoal}
                                        style={{ padding: '0.25rem 0.75rem' }}
                                    >
                                        Delete Goal
                                    </button>
                                </div>
                            ) : (
                                <select
                                    className="form-select"
                                    value={formData.careerGoal}
                                    onChange={(e) => setFormData({ ...formData, careerGoal: e.target.value })}
                                    required
                                >
                                    <option value="">Select your target role...</option>
                                    {roles.map(role => (
                                        <option key={role.id} value={role.name}>{role.name}</option>
                                    ))}
                                </select>
                            )}
                        </div>

                        <div className="form-group">
                            <label className="form-label">Study Hours Per Week</label>
                            <input
                                type="number"
                                className="form-input"
                                value={formData.studyHoursPerWeek}
                                onChange={(e) => setFormData({ ...formData, studyHoursPerWeek: parseInt(e.target.value) })}
                                min="1"
                                max="40"
                                required
                            />
                        </div>

                        <div className="form-group">
                            <label className="form-label">Overall Experience Level</label>
                            <select
                                className="form-select"
                                value={formData.experienceLevel}
                                onChange={(e) => setFormData({ ...formData, experienceLevel: e.target.value })}
                            >
                                <option value="BEGINNER">Beginner</option>
                                <option value="INTERMEDIATE">Intermediate</option>
                                <option value="ADVANCED">Advanced</option>
                            </select>
                        </div>

                        <div className="form-group">
                            <label className="form-label">Current Skills (Click skill, then select level)</label>
                            <div className="skill-tags">
                                {skills.map(skill => {
                                    const userSkill = formData.currentSkills.find(s => s.skillName === skill.name);
                                    return (
                                        <div key={skill.id} className="card" style={{ padding: '0.5rem' }}>
                                            <div style={{ fontWeight: 600, marginBottom: '0.5rem' }}>{skill.name}</div>
                                            <div style={{ display: 'flex', gap: '0.25rem', fontSize: '0.75rem' }}>
                                                <button
                                                    type="button"
                                                    className={`btn ${userSkill?.skillLevel === 'BEGINNER' ? 'btn-primary' : 'btn-secondary'}`}
                                                    style={{ padding: '0.25rem 0.5rem' }}
                                                    onClick={() => toggleSkill(skill.name, 'BEGINNER')}
                                                >
                                                    Beginner
                                                </button>
                                                <button
                                                    type="button"
                                                    className={`btn ${userSkill?.skillLevel === 'INTERMEDIATE' ? 'btn-primary' : 'btn-secondary'}`}
                                                    style={{ padding: '0.25rem 0.5rem' }}
                                                    onClick={() => toggleSkill(skill.name, 'INTERMEDIATE')}
                                                >
                                                    Inter
                                                </button>
                                                <button
                                                    type="button"
                                                    className={`btn ${userSkill?.skillLevel === 'ADVANCED' ? 'btn-primary' : 'btn-secondary'}`}
                                                    style={{ padding: '0.25rem 0.5rem' }}
                                                    onClick={() => toggleSkill(skill.name, 'ADVANCED')}
                                                >
                                                    Advanced
                                                </button>
                                            </div>
                                        </div>
                                    );
                                })}
                            </div>
                            <p className="mt-2" style={{ color: 'var(--text-tertiary)', fontSize: '0.875rem' }}>
                                Selected: {formData.currentSkills.length} skills
                            </p>
                        </div>

                        <button type="submit" className="btn btn-success btn-full" disabled={loading}>
                            {loading ? 'Saving...' : 'Save Profile & Continue'}
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

package com.personal.jello.workout.database;

import android.app.Application;
import android.os.AsyncTask;

import com.personal.jello.workout.dataAccess.WeightTrainingTypeDao;
import com.personal.jello.workout.models.WorkoutType;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WorkoutTypeRepository {
    private WeightTrainingTypeDao weightTrainingTypeDao;

    public WorkoutTypeRepository(Application application) {
        WorkoutDatabase db = WorkoutDatabase.getWorkoutDatabase(application);
        weightTrainingTypeDao = db.weightTrainingTypeDao();
    }

    public List<WorkoutType> getAllTypes() {
        try {
            return new getAllAsyncTask(weightTrainingTypeDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<WorkoutType>> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        getAllAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected List<WorkoutType> doInBackground(final Void... params) {
            List<WorkoutType> Types = weightTrainingAsyncTaskDao.getAll();
            return Types != null ? weightTrainingAsyncTaskDao.getAll() : null;
        }
    }

    public void insertType(WorkoutType type) {
        new insertAsyncTask(weightTrainingTypeDao).execute(type);
    }

    private static class insertAsyncTask extends AsyncTask<WorkoutType, Void, Void> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        insertAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WorkoutType... params) {
            weightTrainingAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateType(WorkoutType type) {
        new updateAsyncTask(weightTrainingTypeDao).execute(type);
    }

    private static class updateAsyncTask extends AsyncTask<WorkoutType, Void, Void> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        updateAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WorkoutType... params) {
            weightTrainingAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public void deleteType(WorkoutType type) {
        new deleteAsyncTask(weightTrainingTypeDao).execute(type);
    }

    private static class deleteAsyncTask extends AsyncTask<WorkoutType, Void, Void> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        deleteAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WorkoutType... params) {
            weightTrainingAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public boolean checkIfDeleteValid(Integer typeId) {
        try {
            return new checkIfDeleteValidAsyncTask(weightTrainingTypeDao).execute(typeId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class checkIfDeleteValidAsyncTask extends AsyncTask<Integer, Void, Boolean> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        checkIfDeleteValidAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final Integer... params) {
           return weightTrainingAsyncTaskDao.checkIfDeleteValid(params[0]);
        }
    }
}

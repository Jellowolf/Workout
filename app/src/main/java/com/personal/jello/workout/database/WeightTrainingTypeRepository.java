package com.personal.jello.workout.database;

import android.app.Application;
import android.os.AsyncTask;

import com.personal.jello.workout.dataAccess.WeightTrainingTypeDao;
import com.personal.jello.workout.models.WeightTrainingType;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WeightTrainingTypeRepository {
    private WeightTrainingTypeDao weightTrainingTypeDao;

    public WeightTrainingTypeRepository(Application application) {
        WorkoutDatabase db = WorkoutDatabase.getWorkoutDatabase(application);
        weightTrainingTypeDao = db.weightTrainingTypeDao();
    }

    public List<WeightTrainingType> getAllTypes() {
        try {
            return new getAllAsyncTask(weightTrainingTypeDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class getAllAsyncTask extends AsyncTask<Void, Void, List<WeightTrainingType>> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        getAllAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected List<WeightTrainingType> doInBackground(final Void... params) {
            List<WeightTrainingType> Types = weightTrainingAsyncTaskDao.getAll();
            return Types != null ? weightTrainingAsyncTaskDao.getAll() : null;
        }
    }

    public void insertType(WeightTrainingType Type) {
        new insertAsyncTask(weightTrainingTypeDao).execute(Type);
    }

    private static class insertAsyncTask extends AsyncTask<WeightTrainingType, Void, Void> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        insertAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingType... params) {
            weightTrainingAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void updateType(WeightTrainingType Type) {
        new updateAsyncTask(weightTrainingTypeDao).execute(Type);
    }

    private static class updateAsyncTask extends AsyncTask<WeightTrainingType, Void, Void> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        updateAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingType... params) {
            weightTrainingAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public void deleteType(WeightTrainingType Type) {
        new deleteAsyncTask(weightTrainingTypeDao).execute(Type);
    }

    private static class deleteAsyncTask extends AsyncTask<WeightTrainingType, Void, Void> {
        private WeightTrainingTypeDao weightTrainingAsyncTaskDao;

        deleteAsyncTask(WeightTrainingTypeDao dao) {
            weightTrainingAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final WeightTrainingType... params) {
            weightTrainingAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}
